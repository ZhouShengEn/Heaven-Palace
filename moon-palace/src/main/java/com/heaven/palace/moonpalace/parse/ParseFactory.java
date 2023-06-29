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
package com.heaven.palace.moonpalace.parse;

import com.heaven.palace.moonpalace.common.constant.GenCoreConstant;
import com.heaven.palace.moonpalace.db.exception.GenerationRunTimeException;
import com.heaven.palace.moonpalace.parse.impl.BeetlParseImpl;
import com.heaven.palace.moonpalace.parse.impl.FreeMarkerParseImpl;


/**
 *
 * @author ZhouShengEn
 * 2022年8月25日
 */
public class ParseFactory {

    public static IParse getParse(String parseType) {
        if (GenCoreConstant.BEETL.equalsIgnoreCase(parseType)) {
            return new BeetlParseImpl();
        }
        if (GenCoreConstant.FREEMARKER.equalsIgnoreCase(parseType)) {
            return new FreeMarkerParseImpl();
        }
        throw new GenerationRunTimeException("模板类型不支持");
    }

}
