/**
 * Copyright (c) 2022-8-25
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at <p> http://www.apache.org/licenses/LICENSE-2.0
 * <p> Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.heaven.palace.moonpalace.core.shiro.check;


/**
 *  检查用接口
 */
public interface ICheck {

    /**
     * 检查指定角色
     * @param permissions
     * @return boolean
     */
    boolean check(Object[] permissions);

    /**
     * 检查全体角色
     * @return boolean
     */
    boolean checkAll();
}
