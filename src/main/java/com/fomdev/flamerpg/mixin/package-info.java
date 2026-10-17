/**
 * Mixin 注入点包。
 *
 * <p>当前为空：本模组尚未对原版类做任何替换或增强。</p>
 *
 * <p>之所以保留该包，是因为 {@code flamerpg.mixins.json} 中声明的 {@code package}
 * 必须真实存在于模组 jar 内。包级注解会使编译器生成 {@code package-info.class}，
 * 从而确保这一点。</p>
 *
 * <p>新增 Mixin 时：在 {@code flamerpg.mixins.json} 的 {@code mixins}（双端）
 * 或 {@code client}（仅客户端）数组中登记类名（相对本包，用 {@code .} 分隔）。</p>
 */
@MethodsReturnNonnullByDefault
package com.fomdev.flamerpg.mixin;

import net.minecraft.MethodsReturnNonnullByDefault;
