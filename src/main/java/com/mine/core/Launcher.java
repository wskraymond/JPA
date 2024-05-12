package com.mine.core;

import org.springframework.aop.target.HotSwappableTargetSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mine.core.inf.MyFas;

public class Launcher {
	/*
	 * at com.mine.core.impl.MyFasImpl.getName(MyFasImpl.java:15)
	at com.mine.core.impl.MyFasImpl$$FastClassByCGLIB$$92f7c37f.invoke(<generated>)
	at net.sf.cglib.proxy.MethodProxy.invoke(MethodProxy.java:191)
	at org.springframework.aop.framework.Cglib2AopProxy$CglibMethodInvocation.invokeJoinpoint(Cglib2AopProxy.java:688)
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:150)
	at com.mine.core.AccessAuditLogAdvice.invoke(AccessAuditLogAdvice.java:12)
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("SpringBeans.xml");
//		ApplicationContext exContext = new FileSystemXmlApplicationContext("C:\\appl\\prog\\conf\\com\\mine\\core\\serviceContext.xml");
//		
//		MyBizSvc bean = (MyBizSvc) context.getBean("myBean");
//		bean.foo();
//		
//		MyBizSvc exBean = (MyBizSvc) exContext.getBean("myBean");
//		exBean.foo();
//		
//		LocalCache localCache = (LocalCache) context.getBean("localCache");
//		((AbstractApplicationContext)context).registerShutdownHook();
		
		/*MyBizSvc myBizSvc = (MyBizSvc)context.getBean("myBean");
		myBizSvc.foo();*/
		
		HotSwappableTargetSource swapper = (HotSwappableTargetSource) context.getBean("swapper");
		MyFas myFasProxy = (MyFas)context.getBean("fas_proxy");
//		MyFas myFas = (MyFas)context.getBean("myFas");
//		myFas.getResultList("fuck");
		MyFas newTarget = (MyFas)context.getBean("urFas");
		Object oldTarget = swapper.swap(newTarget);
		MyFas myFasProxy2 = (MyFas)context.getBean("fas_proxy");
		myFasProxy2.getResultList("");
		myFasProxy.getResultList("");	//same result as above
//		((MyFasImpl)myFasProxy).getName();
		
		/*ProxyFactory p = new ProxyFactory();
		Advice a = (Advice)context.getBean("access_audit_advice");
		p.addAdvice(a);
		p.setTarget(new MyBean());
		
		Advisor[] asls = p.getAdvisors();
		System.out.println(asls[0]);
		
		MyBean myBeanProxy2 = (MyBean)p.getProxy();
		myBeanProxy2.setName("mybean");*/
		
		
//		MyFas httpFas = (MyFas)context.getBean("httpFas");
//		System.out.println(httpFas.getResultList("").get(0));
	}
}
