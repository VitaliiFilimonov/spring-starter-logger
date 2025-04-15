package ru.homework.aspect;

import ch.qos.logback.classic.Level;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.homework.properties.LoggerProperties;

@Aspect
@Component
public class TaskAspect {

    private final ch.qos.logback.classic.Logger logger = (ch.qos.logback.classic.Logger) LoggerFactory.getLogger(TaskAspect.class);

    @Autowired
    public TaskAspect(LoggerProperties loggerProperties) {
        logger.setLevel(Level.valueOf(loggerProperties.getLevel()));
    }

    @Before("execution(* ru.homework.service.TaskService.*(..))")
    public void beforeTaskServiceMethods(JoinPoint joinPoint) {
        logger.info("Начало выполнения метода '{}'", joinPoint.getSignature());
    }

    @AfterReturning(value = "@annotation(ru.homework.annotation.SaveTaskLog)", returning = "result")
    public void saveTaskAfterReturning(Object result) {
        logger.info("Задача с индексом '{}' успешно сохранена!", result);
    }

    @Around("@annotation(ru.homework.annotation.LogTimeInterval)")
    public Object checkAroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();

        Object proceed = joinPoint.proceed();

        long end = System.currentTimeMillis();

        logger.info("Затраченное время на выполнение метода '{}' равно '{} мс'", joinPoint.getSignature().getName(), end - start);

        return proceed;
    }
}
