package com.ktdsuniversity.edu.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.util.StopWatch.TaskInfo;

@Aspect // TimingAspect를 AOP 모듈로 생성한다.
@Component
public class TimingAspect {

	private static final Logger logger = LoggerFactory.getLogger(TimingAspect.class);
	
	// Point-Cut 대상 지정. (Weaving 대상을 지정)
	@Pointcut("execution( public * com.ktdsuniversity.edu..service.impl.*Impl.*(..) )")
	private void aroundTarget() {}
	
	@Around("aroundTarget()") // Pointcut 으로 지정한 대상이 실행 될 때, 
	                          // 아래 코드를 Weaving 해서 원래 코드였던 것처럼 동작을 시킨다.
	// Around : AOP가 개입할 수 있는 모든 관점들을 지칭.
	public Object timingAspect(ProceedingJoinPoint pjp) 
				throws Throwable {
		// Before
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		
		// 원래 실행되어야 할 ServiceImpl클래스의 메소드를 실행시킨다.
		Object returnObject = null;
		try {
			returnObject = pjp.proceed();
		} catch (Throwable e) {
			// After Throwing
			throw e;
		} finally {
			stopWatch.stop();
			
			String className = pjp.getTarget() // 원래 동작이 되어야 할 클래스의 정보.
								  .getClass() // 원래 동작이 되어야 할 클래스
								  .getSimpleName() // 원래 동작이 되어야 할 클래스의 이름
			;
			
			String methodName = pjp.getSignature() // 원래 동작이 되어야 할 메소드의 정보.
								   .getName() // 원래 동작이 되어야 할 메소드의 이름
			;
			
			TaskInfo taskInfo = stopWatch.getTaskInfo()[0];
			long proceedingTimeMillis = taskInfo.getTimeMillis();
			logger.info("{}.{} 걸린시간: {}ms", className, methodName, proceedingTimeMillis);
		}
		
		// After ( After Throwing + After Returning)
		// After Returning
		return returnObject;
	}
	
	
}
