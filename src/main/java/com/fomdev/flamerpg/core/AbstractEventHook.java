package com.fomdev.flamerpg.core;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * 事件钩子基类。
 *
 * <p>构造时通过反射定位子类中标注了 {@link EventHookStatusStorer} 的静态
 * {@code boolean} 字段，若该字段已为 {@code true} 则说明钩子被重复实例化，
 * 直接抛异常，避免同一个钩子在事件总线上被注册两次。</p>
 *
 * <p>查找会沿继承链向上进行，因此子类继承另一个钩子时同样可以命中父类里的
 * 状态字段。</p>
 */
public abstract class AbstractEventHook
{
    protected AbstractEventHook()
    {
        if (this.getInitialized())
            throw new IllegalStateException(
                    "Event hook " + this.getClass().getName() + " has already been initialized");

        this.setInitialized();
    }

    protected boolean getInitialized()
    {
        Field field = this.getTargetField();

        try
        {
            return field.getBoolean(null);
        } catch (IllegalAccessException e)
        {
            throw new IllegalStateException("Unable to read hook status field " + field.getName(), e);
        }
    }

    /** 可被子类覆盖，用于自定义「已初始化」的判定方式。 */
    protected void setInitialized()
    {
        Field field = this.getTargetField();

        try
        {
            field.setBoolean(null, true);
        } catch (IllegalAccessException e)
        {
            throw new IllegalStateException("Unable to write hook status field " + field.getName(), e);
        }
    }

    /**
     * 从当前类开始沿继承链向上查找标注了 {@link EventHookStatusStorer} 的字段。
     *
     * @return 状态字段，保证为静态 {@code boolean}
     * @throws IllegalStateException 找不到字段，或字段类型不合法
     */
    protected Field getTargetField()
    {
        for (Class<?> type = this.getClass(); type != null && type != Object.class; type = type.getSuperclass())
        {
            for (Field field : type.getDeclaredFields())
            {
                if (!field.isAnnotationPresent(EventHookStatusStorer.class))
                    continue;

                int modifiers = field.getModifiers();
                if (field.getType() != boolean.class || !Modifier.isStatic(modifiers))
                    throw new IllegalStateException(
                            "@EventHookStatusStorer must annotate a static boolean field, found "
                                    + type.getName() + "." + field.getName());

                if (!field.trySetAccessible())
                    throw new IllegalStateException(
                            "Cannot access hook status field " + type.getName() + "." + field.getName());

                return field;
            }
        }

        throw new IllegalStateException(
                "Invalid event hook class " + this.getClass().getName()
                        + ": no field annotated with @EventHookStatusStorer found");
    }
}
