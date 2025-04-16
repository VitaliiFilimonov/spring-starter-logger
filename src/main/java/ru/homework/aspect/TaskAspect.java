package ru.homework.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.event.Level;
import org.springframework.beans.factory.annotation.Autowired;
import ru.homework.properties.LoggerProperties;

@Aspect
public class TaskAspect {

    private final Logger logger = LoggerFactory.getLogger(TaskAspect.class);
    
    private final Level level; 
    
    @Autowired
    public TaskAspect(LoggerProperties loggerProperties) {
        this.level = Level.valueOf(loggerProperties.getLevel().toUpperCase());
    }

    @Before("execution(* ru.homework.service.TaskService.*(..))")
    public void beforeTaskServiceMethods(JoinPoint joinPoint) {
        logger.atLevel(level).log("Начало выполнения метода '{}'", joinPoint.getSignature());
    }

    @AfterReturning(value = "@annotation(ru.homework.annotation.SaveTaskLog)", returning = "result")
    public void saveTaskAfterReturning(Object result) {
        logger.atLevel(level).log("Задача с индексом '{}' успешно сохранена!", result);
    }

    @Around("@annotation(ru.homework.annotation.LogTimeInterval)")
    public Object checkAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long end = System.currentTimeMillis();

        logger.atLevel(level).log("Затраченное время на выполнение метода '{}' равно '{} мс'", joinPoint.getSignature().getName(), end - start);

        return proceed;
    }
}
