/**
 * Copyright 2022-8-25
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.heaven.palace.moonpalace.modular.code.service.impl;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.heaven.palace.moonpalace.base.response.ObjectRestResponse;
import com.heaven.palace.moonpalace.common.constant.GenCoreConstant;
import com.heaven.palace.moonpalace.config.properties.GunsProperties;
import com.heaven.palace.moonpalace.db.read.IReadTable;
import com.heaven.palace.moonpalace.db.read.ReadTableFactory;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.exception.BusinessExceptionEnum;
import com.heaven.palace.moonpalace.model.CodeGenModel;
import com.heaven.palace.moonpalace.model.GenBeanEntity;
import com.heaven.palace.moonpalace.model.GenerationEntity;
import com.heaven.palace.moonpalace.modular.code.entity.ScpConnectEntity;
import com.heaven.palace.moonpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.moonpalace.modular.code.model.TemplateFileModel;
import com.heaven.palace.moonpalace.modular.code.model.TemplateModel;
import com.heaven.palace.moonpalace.modular.code.service.IGenService;
import com.heaven.palace.moonpalace.modular.code.service.ITableInfoService;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateFileService;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateService;
import com.heaven.palace.moonpalace.modular.code.vo.GenCodeCommitVO;
import com.heaven.palace.moonpalace.modular.code.vo.GenTableCodeCommitVO;
import com.heaven.palace.moonpalace.modular.system.service.IDbInfoService;
import com.heaven.palace.moonpalace.parse.ParseFactory;
import com.heaven.palace.moonpalace.util.CodeGenUtil;
import com.heaven.palace.moonpalace.util.ConnectionUtil;
import com.heaven.palace.moonpalace.util.NameUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpATTRS;
import com.jcraft.jsch.SftpException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author ZhouShengEn
 * 2022年8月25日
 */
@Service("genService")
@Slf4j
public class GenServiceImpl implements IGenService {

    @Resource
    private ITemplateFileService templateFileService;

    @Resource
    private IDbInfoService dbInfoService;

    @Resource
    private ITemplateService templateService;

    @Value(value = "${sftp.maxConnectTimeout:3000}")
    private Integer sftpMaxConnectTimeout;

    @Value(value = "${sftp.maxKexTimeout:5000}")
    private Integer sftpMaxKexTimeout;

    @Resource
    private GunsProperties gunsProperties;

    @Resource
    private ITableInfoService tableInfoService;

    @Override
    public List<String> loadTemplateFile(List<TemplateModel> templateList) {
        List<String> list = new ArrayList<String>();
        for (TemplateModel templateEntity : templateList) {
            list.add(getTemplateFile(templateEntity));
        }
        return list;
    }

    @Override
    public ObjectRestResponse<List<String>> queryDb(Integer dbId) {
        try {
            DbInfoModel dbInfoModel = new DbInfoModel();
            dbInfoModel.setId(dbId);
            DbInfoModel entity = dbInfoService.selectOne(dbInfoModel);
            ConnectionUtil.init(entity.getDbDriver(), entity.getDbUrl(), entity.getDbUserName(),
                entity.getDbPassword());
            IReadTable readTable = ReadTableFactory.getReadTable(entity.getDbType());
            List<String> list = readTable.getAllDB();
            return new ObjectRestResponse<>().data(list);
        } finally {
            ConnectionUtil.close();
        }
    }

    @Override
    public ObjectRestResponse<List<GenBeanEntity>> queryTable(Integer dbId, String tableName) {
        try {
            DbInfoModel dbInfoModel = new DbInfoModel();
            dbInfoModel.setId(dbId);
            DbInfoModel entity = dbInfoService.selectOne(dbInfoModel);
            ConnectionUtil.init(entity.getDbDriver(), entity.getDbUrl(), entity.getDbUserName(),
                entity.getDbPassword());
            IReadTable readTable = ReadTableFactory.getReadTable(entity.getDbType());
            return new ObjectRestResponse<>().data(readTable.getAllTable(tableName));
        } finally {
            ConnectionUtil.close();
        }
    }

    @Override
    public void genCode(GenCodeCommitVO gcc, HttpServletResponse res) {
        DbInfoModel dbInfoModel = new DbInfoModel();
        dbInfoModel.setId(gcc.getDbId());
        DbInfoModel entity = dbInfoService.selectOne(dbInfoModel);
        CodeGenModel model = new CodeGenModel();
        model.setDbType(GenCoreConstant.MYSQL);
        model.setTableName(gcc.getTableName());
        model.setDbName(gcc.getDbName());
        model.setUrl(entity.getDbUrl());
        model.setPasswd(entity.getDbPassword());
        model.setUsername(entity.getDbUserName());
        if (StringUtils.isEmpty(gcc.getEntityName())) {
            gcc.setEntityName(NameUtil.getEntityHumpName(gcc.getTableName()));
        }
        model.setGenerationEntity(gcc);
        List<TemplateModel> templateList = templateService.getTemplateByIds(gcc.getTemplateIds());
        List<String> templateFileList = loadTemplateFile(templateList);
        List<String> fileList = new ArrayList<>();
        for (int i = 0; i < templateList.size(); i++) {
            model.setParseType(templateList.get(i).getTemplateType());
            model.setFile(templateFileList.get(i));
            fileList.addAll(CodeGenUtil.codeGen(model));
        }
        if (!org.apache.commons.lang3.StringUtils.isAnyEmpty(gcc.getLocalPath(), gcc.getIpAddress(), gcc.getSftpAccount(),
            gcc.getSftpPassword())) {
            writeThisFileList(fileList, templateList, gcc);
        } else {
            downThisFileList(res, fileList, templateList, gcc);
        }
    }

    @Override
    public void genTableCode(GenTableCodeCommitVO gcc, HttpServletResponse res) {
        List<TemplateModel> templateList = templateService.getTemplateByIds(gcc.getTemplateIds());
        final List<String> templateFileList = loadTemplateFile(templateList);
        List<String> fileList = new ArrayList<>();
        GenBeanEntity tableEntity = tableInfoService.getGenBean(gcc.getTableId());
        for (int i = 0; i < templateList.size(); i++) {
            final int index = i;
            fileList.addAll(ParseFactory.getParse(templateList.get(i).getTemplateType()).parse(gcc, tableEntity,
                new ArrayList<String>() {
                    {
                        add(templateFileList.get(index));
                    }
                }));
        }
        if (StringUtils.isNotEmpty(gcc.getLocalPath())) {
            writeThisFileList(fileList, templateList, gcc);
        } else {
            downThisFileList(res, fileList, templateList, gcc);
        }
    }

    private String getTemplateFile(TemplateModel templateEntity) {
        TemplateFileModel entity = new TemplateFileModel();
        entity.setTemplateId(templateEntity.getId());
        return templateFileService.selectOne(entity).getFile();
    }

    /**
     * 下载代码
     */
    private void downThisFileList(HttpServletResponse res, List<String> fileList, List<TemplateModel> templateList, GenerationEntity ge) {
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(res.getOutputStream());
            for (int i = 0; i < fileList.size(); i++) {
                out.putNextEntry(new ZipEntry(getPackagePath(templateList.get(i), ge) + File.separator + getFileName(templateList.get(i), ge)));
                out.write(fileList.get(i).getBytes(), 0, fileList.get(i).getBytes().length);
                out.closeEntry();
            }
            res.setContentType("application/octet-stream");
            res.setHeader("Content-Disposition", "attachment;filename=" + ge.getEntityName() + ".zip");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                out.close();
                res.getOutputStream().flush();
                res.getOutputStream().close();
            } catch (IOException e) {
                log.error(e.getMessage(), e);
            }
        }
    }


    private String getFileName(TemplateModel templateModel, GenerationEntity ge) {
        if (templateModel.getFileName().endsWith("js")) {
            return String.format(templateModel.getFileName(), ge.getEntityName().toLowerCase());
        } else if (templateModel.getFileName().endsWith("html")) {
            return String.format(templateModel.getFileName(), ge.getEntityName().toLowerCase());
        } else if (templateModel.getFileName().endsWith("xml")) {
            return String.format(templateModel.getFileName(), ge.getEntityName());
        } else {
            return String.format(templateModel.getFileName(), ge.getEntityName());
        }
    }


    private String getPackagePath(TemplateModel templateModel, GenerationEntity ge) {
        String templatePath = String.format(templateModel.getTemplatePath(), ge.getEntityName().toLowerCase());
        if (templateModel.getFileName().endsWith("js")) {
            return ge.getJsPackage().replaceAll("\\.", "\\/")
                + (StringUtils.isNotEmpty(templateModel.getTemplatePath()) ? "/"
                + templatePath.replaceAll("\\.", "\\/") : "");
        } else if (templateModel.getFileName().endsWith("html")) {
            return ge.getHtmlPackage().replaceAll("\\.", "\\/")
                + (StringUtils.isNotEmpty(templateModel.getTemplatePath()) ? "/"
                + templatePath.replaceAll("\\.", "\\/") : "");
        } else if (templateModel.getFileName().endsWith("xml")) {
            return ge.getXmlPackage().replaceAll("\\.", "\\/")
                + (StringUtils.isNotEmpty(templateModel.getTemplatePath()) ? "/"
                + templatePath.replaceAll("\\.", "\\/") : "");
        } else {
            return ge.getCodePackage().replaceAll("\\.", "\\/")
                + (StringUtils.isNotEmpty(templateModel.getTemplatePath()) ? "/"
                + templatePath.replaceAll("\\.", "\\/") : "");
        }
    }


    /**
     * 本地输出代码
     */
    private void writeThisFileList(List<String> fileList, List<TemplateModel> templateList, GenerationEntity ge) {
        if (!gunsProperties.getGenLocal()) {
            throw new BusinessException(500, "不允许生成在本地！");
        }
        for (int i = 0; i < fileList.size(); i++) {

            ScpConnectEntity scpConnectEntity = new ScpConnectEntity();
            scpConnectEntity.setTargetPath(ge.getLocalPath());
            scpConnectEntity.setUrl(ge.getIpAddress());
            scpConnectEntity.setPassWord(ge.getSftpPassword());
            scpConnectEntity.setUserName(ge.getSftpAccount());
            Connection connection = null;
            ch.ethz.ssh2.Session session = null;
            try {
                connection = new Connection(scpConnectEntity.getUrl());
                connection.connect(null, sftpMaxConnectTimeout, sftpMaxKexTimeout);
                if (!connection.authenticateWithPassword(scpConnectEntity.getUserName(), scpConnectEntity.getPassWord())) {
                    throw new RuntimeException("SSH连接服务器失败");
                }
                session = connection.openSession();

                SCPClient scpClient = connection.createSCPClient();
                String filePath = ge.getLocalPath() + File.separator +
                    (StringUtils.isNotEmpty(templateList.get(i).getLocalPath()) ?
                        templateList.get(i).getLocalPath().replaceAll("\\.", "\\/") + File.separator : "") +
                    getPackagePath(templateList.get(i), ge);
                scpConnectEntity.setTargetPath(filePath.replaceAll("\\\\", "/"));
                createDir(scpConnectEntity);
                scpClient.put(fileList.get(i).getBytes(StandardCharsets.UTF_8), getFileName(templateList.get(i), ge), scpConnectEntity.getTargetPath());

            } catch (Exception e) {
                e.printStackTrace();
                throw new BusinessException(BusinessExceptionEnum.SFTP_CONNECTED_ERROR);
            } finally {
                if (null != session) {
                    session.close();
                }
                if (null != connection) {
                    connection.close();
                }
            }


            // 文件路径包括 本地项目路径 + 项目相对路径 + 包路径 + 类的自我路径
            //            String filePath = "\\\\172.16.101.125\\"+localPath + File.separator +
            //                    (StringUtils.isNotEmpty(templateList.get(i).getLocalPath()) ?
            //                            templateList.get(i).getLocalPath().replaceAll("\\.", "\\/") + File.separator : "") +
            //                    getPackagePath(templateList.get(i), ge);
            //            File path = new File(filePath + File.separator);
            //            if (!path.exists()) {
            //                path.mkdirs();
            //            }
            //
            //            try {
            //                File file = new File(filePath + File.separator + getFileName(templateList.get(i), ge));
            //                FileUtils.writeStringToFile(file, fileList.get(i), encoded);
            //            } catch (IOException e) {
            //                log.error(e.getMessage(), e);
            //            }

        }
    }

    private boolean createDir(ScpConnectEntity scpConnectEntity) throws JSchException {

        JSch jsch = new JSch();
        com.jcraft.jsch.Session sshSession = null;
        Channel channel = null;
        try {
            sshSession = jsch.getSession(scpConnectEntity.getUserName(), scpConnectEntity.getUrl(), 22);
            sshSession.setPassword(scpConnectEntity.getPassWord());
            Properties sshConfig = new Properties();
            sshConfig.put("StrictHostKeyChecking", "no");
            sshSession.setConfig(sshConfig);
            sshSession.connect(sftpMaxConnectTimeout);
            channel = sshSession.openChannel("sftp");
            channel.connect(sftpMaxConnectTimeout);
        } catch (JSchException e) {
            e.printStackTrace();
            throw new JSchException("SFTP连接服务器失败" + e.getMessage());
        }
        ChannelSftp channelSftp = (ChannelSftp) channel;
        if (isDirExist(scpConnectEntity.getTargetPath(), channelSftp)) {
            channel.disconnect();
            channelSftp.disconnect();
            sshSession.disconnect();
            return true;
        } else {
            String pathArry[] = scpConnectEntity.getTargetPath().split("/");
            StringBuffer filePath = new StringBuffer("/");
            for (String path : pathArry) {
                if (path.equals("")) {
                    continue;
                }
                filePath.append(path + "/");
                try {
                    if (isDirExist(filePath.toString(), channelSftp)) {
                        channelSftp.cd(filePath.toString());
                    } else {
                        // 建立目录
                        channelSftp.mkdir(filePath.toString());
                        // 进入并设置为当前目录
                        channelSftp.cd(filePath.toString());
                    }
                } catch (SftpException e) {
                    e.printStackTrace();
                    throw new JSchException("SFTP无法正常操作服务器" + e.getMessage());
                }
            }
        }
        channel.disconnect();
        channelSftp.disconnect();
        sshSession.disconnect();
        return true;
    }

    private boolean isDirExist(String directory, ChannelSftp channelSftp) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = channelSftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }

}
