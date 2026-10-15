package com.fomdev.flamerpg.core;

import java.lang.reflect.Field;

public abstract class AbstractEventHook
{
    public AbstractEventHook()
    {
        if (!this.getInitialized())
            throw new IllegalStateException("Current class already initialized");

        this.setInitialized();
    }

    protected boolean getInitialized()
    {
        Field annotatedField = this.getTargetField();

        try
        {
            annotatedField.setAccessible(true);
            return annotatedField.getBoolean(null);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to get field");
        }
    }

    // Override this if needed
    protected void setInitialized()
    {
        Field annotatedField = this.getTargetField();

        try
        {
            annotatedField.setAccessible(true);
            annotatedField.setBoolean(null, true);
        } catch (IllegalAccessException e)
        {
            e.printStackTrace();
            throw new IllegalStateException("Unable to set field");
        }
    }

    protected Field getTargetField()
    {
        Class<? extends AbstractEventHook> self = this.getClass();
        Field[] fields = self.getDeclaredFields();
        if (fields.length == 0) throw new IllegalStateException("Invalid EventHook class. No required fields detected");

        Field annotatedField = null;
        for (Field field: fields)
            if (field.isAnnotationPresent(EventHookStatusStorer.class))
            {
                annotatedField = field;
                break;
            }

        if (annotatedField == null) throw new IllegalStateException("Invalid EventHook class. No required fields detected");
        return annotatedField;
    }
}