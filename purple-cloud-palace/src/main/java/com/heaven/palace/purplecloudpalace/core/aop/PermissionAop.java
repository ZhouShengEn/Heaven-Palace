/**
 * Copyright (c) 2022-8-25
 * License, Version 2.0 (the "License"); you may not use this file except in compliance with the
 * License. You may obtain a copy of the License at <p> http://www.apache.org/licenses/LICENSE-2.0
 * <p> Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.heaven.palace.purplecloudpalace.core.aop;

import com.heaven.palace.purplecloudpalace.common.annotion.Permission;
import com.heaven.palace.purplecloudpalace.core.shiro.check.PermissionCheckManager;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import javax.naming.NoPermissionException;
import java.lang.reflect.Method;

/**
 * AOP 权限自定义检查
 */
@Aspect
@Component
public class PermissionAop {

    @Pointcut(value = "@annotation(com.heaven.palace.purplecloudpalace.common.annotion.Permission)")
    private void cutPermission() {

    }

    @Around("cutPermission()")
    public Object doPermission(ProceedingJoinPoint point) throws Throwable {
        MethodSignature ms = (MethodSignature) point.getSignature();
        Method method = ms.getMethod();
        Permission permission = method.getAnnotation(Permission.class);
        Object[] permissions = permission.value();
        if (permissions == null || permissions.length == 0) {
            //检查全体角色
            boolean result = PermissionCheckManager.checkAll();
            if (result) {
                return point.proceed();
            } else {
                throw new NoPermissionException();
            }
        } else {
            //检查指定角色
            boolean result = PermissionCheckManager.check(permissions);
            if (result) {
                return point.proceed();
            } else {
                throw new NoPermissionException();
            }
        }

    }

}
