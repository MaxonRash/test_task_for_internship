package com.game.aspect;

import com.game.entity.Player;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//@Component
//@Aspect
public class PlayerExperienceCalculationAspect {
//
////    @Before("execution(* updUpdatePlayer(..))")
////    public void loggingUpdate() {
////        System.out.println("update player log");
////    }
//
//    @Around("execution(* updUpdatePlayer(long, Object))")
//    public Object beforeUpdateOrCreateExpCalc(ProceedingJoinPoint joinPoint) {
//        long id = (long) joinPoint.getArgs()[0];
//        Player player = (Player) joinPoint.getArgs()[1];
//
//        player.setLevel(20);
//        player.setUntilNextLevel(500);
//
//        Object[] args = new Object[]{id, player};
////        args[0] = player;
//        Player targetMethodResult;
//        try {
//            targetMethodResult = (Player) joinPoint.proceed(args);
//        } catch (Throwable e) {
//            throw new RuntimeException(e);
//        }
//        return targetMethodResult;
//    }
}
