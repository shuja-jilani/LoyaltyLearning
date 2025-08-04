package com.loyaltyProgramPOC.loyaltyProgram.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    // Logs execution time for service methods
    @Around("execution(* com.loyaltyProgramPOC.loyaltyProgram.service.EmployeeService.*(..))")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        logger.info("Method: {} called with arguments: {}", joinPoint.getSignature(), Arrays.toString(joinPoint.getArgs()));
        System.out.println("Method called: " + joinPoint.getSignature());

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();  // execute the actual method
        long end = System.currentTimeMillis();

        logger.info("Method: {} completed in {}ms", joinPoint.getSignature(), (end - start));
        System.out.println("Execution time: " + (end - start) + "ms");

        return result;
    }

    // Logs exception thrown by service methods
    @AfterThrowing(
            pointcut = "execution(* com.loyaltyProgramPOC.loyaltyProgram.service.EmployeeService.*(..))",
            throwing = "ex"
    )
    public void logException(JoinPoint joinPoint, Throwable ex) {
        logger.error("Exception in method: {} with message: {}", joinPoint.getSignature(), ex.getMessage());
        System.err.println("Exception in method: " + joinPoint.getSignature() + " -> " + ex.getMessage());
    }
}

