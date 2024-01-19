/**
 * @author 10733
 * @date 2024/1/14 23:12
 * @description: 业务领域层
 * Entity 具有唯一业务标识，持有自己的业务属性和业务行为，关注于生命周期，只能get不允许set
 * Value Object 非必要唯一业务标识，定义后是不可变的，标识某种业务状态，可以拥有自己的业务属性和业务行为，用于将隐性的属性显性地表示出来
 * Service 如果某种行为无法归类给Entity和Value时，则对该行为建立相应的领域服务，适用于可能存在的通用业务逻辑
 * Repository 和Entity紧密相连，但只定义契约，实现交由基础设施层
 */
package com.heaven.palace.brightpalace.domain;