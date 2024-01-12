/**
 * Copyright 2022-8-25
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.heaven.palace.moonpalace.modular.code.controller;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import com.heaven.palace.moonpalace.base.controller.BaseController;
import com.heaven.palace.moonpalace.base.tips.ErrorTip;
import com.heaven.palace.moonpalace.common.constant.GenCoreConstant;
import com.heaven.palace.moonpalace.config.properties.GunsProperties;
import com.heaven.palace.moonpalace.core.shiro.ShiroKit;
import com.heaven.palace.moonpalace.db.read.IReadTable;
import com.heaven.palace.moonpalace.db.read.ReadTableFactory;
import com.heaven.palace.moonpalace.exception.BusinessException;
import com.heaven.palace.moonpalace.model.BaseModel;
import com.heaven.palace.moonpalace.model.CodeGenModel;
import com.heaven.palace.moonpalace.model.GenBeanEntity;
import com.heaven.palace.moonpalace.model.GenerationEntity;
import com.heaven.palace.moonpalace.model.RequestModel;
import com.heaven.palace.moonpalace.model.ResponseModel;
import com.heaven.palace.moonpalace.modular.code.entity.ScpConnectEntity;
import com.heaven.palace.moonpalace.modular.code.model.DbInfoModel;
import com.heaven.palace.moonpalace.modular.code.model.GenParamModel;
import com.heaven.palace.moonpalace.modular.code.model.TableInfoModel;
import com.heaven.palace.moonpalace.modular.code.model.TemplateGroupModel;
import com.heaven.palace.moonpalace.modular.code.model.TemplateModel;
import com.heaven.palace.moonpalace.modular.code.service.IGenParamService;
import com.heaven.palace.moonpalace.modular.code.service.IGenService;
import com.heaven.palace.moonpalace.modular.code.service.ITableInfoService;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateGroupService;
import com.heaven.palace.moonpalace.modular.code.service.ITemplateService;
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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
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
 * @author ZhouShengEn 2022年8月25日
 */
@Controller
@RequestMapping("code")
@RefreshScope
public class GenController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GenController.class);

    private String PREFIX = "/code/gen/";

    @Resource
    private IGenService genService;
    @Resource
    private ITemplateService templateService;
    @Resource
    private IDbInfoService dbInfoService;
    @Resource
    private ITemplateGroupService templateGroupService;
    @Resource
    private IGenParamService genParamService;
    @Resource
    private GunsProperties gunsProperties;
    @Resource
    private ITableInfoService tableInfoService;
    @Value(value = "${sftp.maxConnectTimeout:3000}")
    private Integer sftpMaxConnectTimeout;
    @Value(value = "${sftp.maxKexTimeout:5000}")
    private Integer sftpMaxKexTimeout;

    /**
     * 跳转到首页
     */
    @RequestMapping("")
    public String index(Model modelMap) {
        TemplateGroupModel model = new TemplateGroupModel();
        model.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("groups", templateGroupService.selectList(model));
        GenParamModel params = new GenParamModel();
        model.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("params", genParamService.selectList(params));
        return PREFIX + "index.html";
    }

    /**
     * 跳转到首页
     */
    @RequestMapping("/tableGen/{id}")
    public String tableGen(Model modelMap, @PathVariable Integer id) {
        TemplateGroupModel model = new TemplateGroupModel();
        model.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("groups", templateGroupService.selectList(model));
        GenParamModel params = new GenParamModel();
        model.setUserId(ShiroKit.getUser().getId());
        modelMap.addAttribute("params", genParamService.selectList(params));
        modelMap.addAttribute("table", tableInfoService.selectOne(new TableInfoModel(id)));
        return PREFIX + "tableinfo_gen.html";
    }

    @RequestMapping(value = "queryDatabses")
    @ResponseBody
    public ResponseModel queryDatabses(DbInfoModel entity, RequestModel form) {
        try {
            entity = dbInfoService.selectOne(entity);
            ConnectionUtil.init(entity.getDbDriver(), entity.getDbUrl(), entity.getDbUserName(),
                    entity.getDbPassword());
            IReadTable readTable = ReadTableFactory.getReadTable(entity.getDbType());
            List<String> list = readTable.getAllDB();
            List<BaseModel> dblist = new ArrayList<BaseModel>();
            BaseModel info;
            for (String db : list) {
                info = new BaseModel(db);
                dblist.add(info);
            }
            return ResponseModel.ins(dblist);
        } finally {
            ConnectionUtil.close();
        }
    }

    @RequestMapping(value = "queryTables")
    @ResponseBody
    public ResponseModel queryTables(DbInfoModel entity, String dbName, RequestModel form) {
        try {
            entity = dbInfoService.selectOne(entity);
            ConnectionUtil.init(entity.getDbDriver(), entity.getDbUrl(), entity.getDbUserName(),
                    entity.getDbPassword());
            IReadTable readTable = ReadTableFactory.getReadTable(entity.getDbType());
            return ResponseModel.ins(readTable.getAllTable(dbName));
        } finally {
            ConnectionUtil.close();
        }
    }

    @RequestMapping(value = "genCode")
    @ResponseBody
    public Object genCode(DbInfoModel entity, String dbName, String tableName, String localPath, String encoded, GenerationEntity ge,
                        HttpServletRequest req, HttpServletResponse res) {
        entity = dbInfoService.selectOne(entity);
        String[] templates = req.getParameterValues("templates[]");
        CodeGenModel model = new CodeGenModel();
        model.setDbType(GenCoreConstant.MYSQL);
        model.setTableName(tableName);
        model.setDbName(dbName);
        model.setUrl(entity.getDbUrl());
        model.setPasswd(entity.getDbPassword());
        model.setUsername(entity.getDbUserName());
        if (StringUtils.isEmpty(ge.getEntityName())) {
            ge.setEntityName(NameUtil.getEntityHumpName(tableName));
        }
        model.setGenerationEntity(ge);
        List<TemplateModel> templateList = templateService.getTemplateByIds(templates);
        List<String> templateFileList = genService.loadTemplateFile(templateList);
        List<String> fileList = new ArrayList<String>();
        for (int i = 0; i < templateList.size(); i++) {
            model.setParseType(templateList.get(i).getTemplateType());
            model.setFile(templateFileList.get(i));
            fileList.addAll(CodeGenUtil.codeGen(model));
        }
        if (!org.apache.commons.lang3.StringUtils.isAnyEmpty(localPath, ge.getIpAddress(), ge.getSftpAccount(), ge.getSftpPassword())) {
            return writeThisFileList(localPath, encoded, fileList, templateList, ge);
        } else {
            downThisFileList(res, fileList, templateList, ge);
            return res;
        }
        
    }


    @RequestMapping(value = "genTableCode")
    public void genTableCode(Integer tableId, String localPath, String encoded, GenerationEntity ge,
                             HttpServletRequest req, HttpServletResponse res) {
        String[] templates = req.getParameterValues("templates[]");
        List<TemplateModel> templateList = templateService.getTemplateByIds(templates);
        final List<String> templateFileList = genService.loadTemplateFile(templateList);
        List<String> fileList = new ArrayList<String>();
        GenBeanEntity tableEntity = tableInfoService.getGenBean(tableId);
        for (int i = 0; i < templateList.size(); i++) {
            final int index = i;
            fileList.addAll(ParseFactory.getParse(templateList.get(i).getTemplateType()).parse(ge, tableEntity,
                    new ArrayList<String>() {
                        {
                            add(templateFileList.get(index));
                        }
                    }));
        }
        if (StringUtils.isNotEmpty(localPath)) {
            writeThisFileList(localPath, encoded, fileList, templateList, ge);
        } else {
            downThisFileList(res, fileList, templateList, ge);
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

    /**
     * 本地输出代码
     */
    private Object writeThisFileList(String localPath, String encoded, List<String> fileList, List<TemplateModel> templateList, GenerationEntity ge) {
        if (!gunsProperties.getGenLocal()) {
            throw new BusinessException(500, "不允许生成在本地！");
        }
        for (int i = 0; i < fileList.size(); i++) {

            ScpConnectEntity scpConnectEntity = new ScpConnectEntity();
            scpConnectEntity.setTargetPath(localPath);
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
                String filePath = localPath + File.separator +
                        (StringUtils.isNotEmpty(templateList.get(i).getLocalPath()) ?
                                templateList.get(i).getLocalPath().replaceAll("\\.", "\\/") + File.separator : "") +
                        getPackagePath(templateList.get(i), ge);
                scpConnectEntity.setTargetPath(filePath.replaceAll("\\\\", "/"));
                createDir(scpConnectEntity);
                scpClient.put(fileList.get(i).getBytes(StandardCharsets.UTF_8), getFileName(templateList.get(i), ge), scpConnectEntity.getTargetPath());

            } catch (Exception e) {
                e.printStackTrace();
                return new ErrorTip(500, e.getMessage());
            } finally {
                if (null != session) {
                    session.close();
                }
                if (null != connection) {
                    connection.close();
                }
            }


            // 文件路径包括 本地项目路径 + 项目相对路径 + 包路径 + 类的自我路径
//            String filePath = "\\\\127.0.0.1\\"+localPath + File.separator +
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
//                LOGGER.error(e.getMessage(), e);
//            }
            
        }
        return SUCCESS_TIP;
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
            LOGGER.error(e.getMessage(), e);
        } finally {
            try {
                out.close();
                res.getOutputStream().flush();
                res.getOutputStream().close();
            } catch (IOException e) {
                LOGGER.error(e.getMessage(), e);
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


}
