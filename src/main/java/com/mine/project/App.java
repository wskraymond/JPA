package com.mine.project;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;

/**
 * Hello world!
 *
 */
public class App 
{
	private final static String PERSISTENCE_UNIT_NAME = "myJPA";
	private final static EntityManagerFactory EMFACTORY;
	private final static String SELECT_JPQL = "select po from Job po where po.minSalary >= :minSalary order by po.jobTitle asc";
	static{
		System.out.println("Start");
		EMFACTORY = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	}
	
    @SuppressWarnings("unchecked")
	public static void main( String[] args )
    {
//		handleEmp();
//    	handleDpt();
//    	handleStaff();
//    	handleVersion();
//    	pessimisticForceIncrement();
//    	handleCascade();
//    	handleSecondLVCache();
//    	handleSecondLVCacheForNonStrict();
//    	applicationLevelRepeatableRead();
//    	firstlvAndSecondlv();
    	queryCacheAnd2lvCache();
    }
    
    private static void queryCacheAnd2lvCache(){
    	EntityManager em1 = null, em2 = null;
		try {
			   System.out.println("------------Session 1 start------------------");
			   em1 = EMFACTORY.createEntityManager();
			   em1.getTransaction().begin();
			   /**
			    * Reference JPA: http://stackoverflow.com/questions/3569996/hibernate-entity-manager-how-to-cache-queries
			    */
			   Query q = em1.createQuery(SELECT_JPQL);		//like JDBC: conn.createPreparedStatement()
			   q.setParameter("minSalary", BigDecimal.valueOf(6000));	//Input Parameters
			   q.setHint("org.hibernate.cacheable", Boolean.TRUE);
			   q.setHint("org.hibernate.cacheRegion", "query.findJobList");
			   
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   List<Job> list = q.getResultList();	
			   System.out.println(list);
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   
			   em1.getTransaction().commit();
			   displayPersistenceContext(em1);
			   em1.close();
			   System.out.println("------------Session 1 end------------------");
			   
			   System.out.println("------------Session 2 start------------------");
			   em2 = EMFACTORY.createEntityManager();
			   em2.getTransaction().begin();
			   
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   Job job = em2.find(Job.class, "FI_MGR");
			   System.out.println(job);
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));

			   Query q2 = em2.createQuery(SELECT_JPQL);		//like JDBC: conn.createPreparedStatement()
			   q2.setParameter("minSalary", BigDecimal.valueOf(6000));	//Input Parameters
			   q2.setHint("org.hibernate.cacheable", Boolean.TRUE);
			   q2.setHint("org.hibernate.cacheRegion", "query.findJobList");
			   List<Job> list2 = q2.getResultList();	
			   System.out.println(list2);
			   
			   em2.getTransaction().commit();
			   displayPersistenceContext(em2);
			   em2.close();
			   System.out.println("------------Session 2 end------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}

		EMFACTORY.close();
		System.out.println("End");
    }
    
    private static void firstlvAndSecondlv(){
    	EntityManager em1 = null, em2 = null, em3 = null, em4 = null;
    	Job job1 = null, job2 = null, job3 = null, job4 = null;
		try {
			   System.out.println("------------Session 1 start------------------");
			   em1 = EMFACTORY.createEntityManager();
			   Session session = (Session)em1.getDelegate();
			   em1.getTransaction().begin();
			   
			   displayPersistenceContext(em1);
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   job1 = em1.find(Job.class, "FI_MGR");
			   System.out.println(job1);
			   System.out.println("1lv cache: " + em1.contains(job1));
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   
			   System.out.println("------------Session 2 start------------------");
			   em2 = EMFACTORY.createEntityManager();
			   em2.getTransaction().begin();
			   
			   displayPersistenceContext(em2);
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   job2 = em2.find(Job.class, "FI_MGR");
			   job2.setMaxSalary(job2.getMaxSalary().add(BigDecimal.valueOf(1L)));
			   System.out.println("update");
			   em2.flush();
			   System.out.println(job2);
			   
			   System.out.println("------------Session 4 start------------------");
			   em4 = EMFACTORY.createEntityManager();
			   em4.getTransaction().begin();
			   
			   displayPersistenceContext(em4);
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   /**
			    * non-strict-write for session 2: read from db for session 4
			    * Asynchronous write-through for session 2: read from db for session 4
			    */
			   job4 = em4.find(Job.class, "FI_MGR");
			   System.out.println(job4);
			   System.out.println("1lv cache: " + em4.contains(job4));
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   
			   em4.getTransaction().commit();
			   displayPersistenceContext(em4);
			   em4.close();
			   System.out.println("------------Session 4 end------------------");
			   
			   System.out.println("1lv cache: " + em2.contains(job2));
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   
			   /**
			    * non-strict-write for session 2: after commit , before evict, out-of-sync for new transaction (e.g session 5)
			    */
			   em2.getTransaction().commit();
			   System.out.println("session 2 commit");
			   
			   displayPersistenceContext(em2);
			   em2.close();
			   System.out.println("------------Session 2 end------------------");
			   displayPersistenceContext(em1);
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   /**
			    * llv is separate from 2lv 
			    * Repeatable Read in first-level cache
			    */
			   job1 = em1.find(Job.class, "FI_MGR");
			   System.out.println(job1);
			   System.out.println("1lv cache: " + em1.contains(job1));
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   
			   em1.getTransaction().commit();
			   displayPersistenceContext(em1);
			   em1.close();
			   System.out.println("------------Session 1 end------------------");
			   
			   System.out.println("------------Session 3 start------------------");
			   em3 = EMFACTORY.createEntityManager();
			   em3.getTransaction().begin();
			   
			   displayPersistenceContext(em3);
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   /**
			    * non-strict-write for session 2: read from db for session 3
			    * Asynchronous write-through for session 2: read from 2lv cache for session 3
			    */
			   job3 = em3.find(Job.class, "FI_MGR");
			   System.out.println(job3);
			   System.out.println("1lv cache: " + em3.contains(job3));
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   
			   em3.getTransaction().commit();
			   displayPersistenceContext(em3);
			   em3.close();
			   System.out.println("------------Session 3 end------------------");
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		EMFACTORY.close();
		System.out.println("End");
    }
    
    private static void applicationLevelRepeatableRead(){
    	EntityManager em = null;
		try {
			   em = EMFACTORY.createEntityManager();
			   Session session = (Session)em.getDelegate();
			   em.getTransaction().begin();
			   
			   /*
			    * read-through: no data found in first level cache , then read it from database 
			    * and cache it
			    */
			   Superman superman = em.find(Superman.class, 1L);
			   System.out.println(superman);
			   
			   /*
			    * read-through: entity managed by context , found in  first level cache
			    * then read it from cache
			    */
			   superman = em.find(Superman.class, 1L);
			   System.out.println(superman);
			   
			   
			   
			   /*
			    * If another transaction change superman's skill(field) to another value
			    * and commit it at this time
			    */
			   
			   /*
			    * execute HQL to select Entities : hit database
			    * Application-level repeatable read: if an entity from result is found in first level cache
			    * then still use the stale one but not the fresh one   
			    */
			   Query q = em.createQuery("select po from Superman po");		
			   List<Superman> l = q.getResultList();
			   System.out.println(l);
			   
			   /*
			    * execute HQL to select a value (non-entity) : hit database
			    * Non-caching handling for non-entity
			    * So , fresh value is read. 
			    */
			   q = em.createQuery("select po.skill from Superman po where po.uid = :uid");	
			   q.setParameter("uid", 1L);
			   String skill = (String)q.getSingleResult();
			   System.out.println(skill);
			   
			   
			   /**
			    * To get fresh entity: 1. refresh 2. detach and fetch
			    */
			   em.refresh(superman);
			   
			   em.detach(superman);
			   superman = em.find(Superman.class, 1L);
			   System.out.println(superman);
			   
			   em.getTransaction().commit();
			   displayPersistenceContext(em);
			   em.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		EMFACTORY.close();
		System.out.println("End");
    }
    private static void handleSecondLVCacheForNonStrict(){
    	EntityManager em = null;
		try {
			   em = EMFACTORY.createEntityManager();
			   Session session = (Session)em.getDelegate();
			   em.getTransaction().begin();
			   
			   Job job = em.find(Job.class, "FI_MGR");
			   job.setMaxSalary(BigDecimal.valueOf(4379));

			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   
			   em.getTransaction().commit();
			   displayPersistenceContext(em);
			   em.close();
			   
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   //------------------------second session-------------------------------------
			   
			   em = EMFACTORY.createEntityManager();
			   em.getTransaction().begin();
			   
			   job = em.find(Job.class, "FI_MGR");
			   
			   em.getTransaction().commit();
			   displayPersistenceContext(em);
			   em.close();
			   
			   System.out.println("2lv cache: " + EMFACTORY.getCache().contains(Job.class, "FI_MGR"));
			   
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		EMFACTORY.close();
		System.out.println("End");
    }
    
    private static void handleSecondLVCache(){
    	EntityManager em = null;
		try {
			   em = EMFACTORY.createEntityManager();
			   Session session = (Session)em.getDelegate();
			   em.getTransaction().begin();
			   
			   Job job = em.find(Job.class, "FI_MGR");
			   /**
			    *  if readOnly , java.lang.UnsupportedOperationException: Can't write to a readonly object
			    */
			   job.setMaxSalary(BigDecimal.valueOf(1329));
			   /**
			    * https://vladmihalcea.com/2015/04/27/how-does-hibernate-read_only-cacheconcurrencystrategy-work/
			    * It workd in hibernate 4.X
			    */
//			   session.delete(job);
//			   em.flush();
			   System.out.println(job);
			   
			   Department dept = em.find(Department.class, 10L);
			   dept.setDepartmentName("New Head");
			   
/*			   Employee newE = new Employee();
			   newE.setFirstName("raymond");
			   newE.setLastName("wong");
			   newE.setEmail("abc" + new Date().toString().substring(15, 20));
			   newE.setDepartment(dept);
//			   newE.setManager(e1.getManager());
			   newE.setJobId(job.getUid());
			   newE.setPhoneNumber("123423");
			   newE.setHireDate(new Date());
			   
			   dept.getEmployees().add(newE);*/
			   
			  
			   /*dept.getEmployees().get(0).setHireDate(new Date());*/
			  
			   /**
			    * The HQL UPDATE clears the Collection Cache, 
			    * so Hibernate will have to reload it from the database when the collection is accessed afterwards.
			    */
			  /* em.createQuery("update Employee po set po.hireDate = :hireDate")
			   .setParameter("hireDate", new Date()).executeUpdate();*/
			   
			   /**
			    * , for native queries you need to instruct Hibernate what regions the statement should invalidate. 
			    * If you don't specify any such region, Hibernate will clear all second level cache regions.
			    */
			   Query nativeQuery =  em.createNativeQuery("update HR.EMPLOYEES set HIRE_DATE = ?")
			   .setParameter(1, new Date());
			   nativeQuery.unwrap(org.hibernate.SQLQuery.class).addSynchronizedEntityClass(Employee.class);
			   nativeQuery.executeUpdate();
			   
			   System.out.println(dept);
//			   em.detach(dept);	//detach Department
			   
			   
			   /**
			    * Reference JPA: http://stackoverflow.com/questions/3569996/hibernate-entity-manager-how-to-cache-queries
			    */
			   Query q = em.createQuery(SELECT_JPQL);		//like JDBC: conn.createPreparedStatement()
			   q.setParameter("minSalary", BigDecimal.valueOf(6000));	//Input Parameters
			   q.setHint("org.hibernate.cacheable", Boolean.TRUE);
			   q.setHint("org.hibernate.cacheRegion", "query.findJobList");
			   List<Job> list = q.getResultList();	
			   System.out.println(list);
			   
			   em.getTransaction().commit();
			   displayPersistenceContext(em);
			   em.close();
			   
			   System.out.println(EMFACTORY.getCache());
			   //------------------------second session-------------------------------------
			   
			   em = EMFACTORY.createEntityManager();
			   em.getTransaction().begin();
			   
			   job = em.find(Job.class, "FI_MGR");
			   System.out.println(job);
			   
			   dept = em.find(Department.class, 10L);
			   System.out.println(dept);
			   
			   Query q2 = em.createQuery(SELECT_JPQL);		//like JDBC: conn.createPreparedStatement()
			   q2.setParameter("minSalary", BigDecimal.valueOf(6001));	//Input Parameters
			   q2.setHint("org.hibernate.cacheable", Boolean.TRUE);
			   q2.setHint("org.hibernate.cacheRegion", "query.findJobList");
			   List<Job> list2 = q2.getResultList();	
			   System.out.println(list2);
			   
			   em.getTransaction().commit();
			   displayPersistenceContext(em);
			   em.close();
			   
		} catch (Exception e) {
			e.printStackTrace();
		}

		
		EMFACTORY.close();
		System.out.println("End");
    }
    
    private static void handleCascade(){
    	EntityManager em = EMFACTORY.createEntityManager();
    	Session session = (Session)em.getDelegate();
		try {
			   em.getTransaction().begin();
			   
			   Department persistedDpt = em.find(Department.class, 10L);
			   persistedDpt.setDepartmentName("amdin");
			   em.detach(persistedDpt);
			   System.out.println(persistedDpt);
			   session.buildLockRequest(new LockOptions(LockMode.PESSIMISTIC_WRITE)).setScope(true).lock(persistedDpt);
			   System.out.println(persistedDpt);
			   
			   System.out.println("Commit");
			   em.getTransaction().commit();				//dirty check for update sql
			   
				//flush for transactional write behind
				displayPersistenceContext(em);				//sql in queue already removed 
									//PO is sync but still in cached
				//That means session can be still be used to next transaction but non-thread-safe
				
				System.out.println(persistedDpt);
				
				em.clear();
				persistedDpt = em.find(Department.class, 10L);
				System.out.println(persistedDpt);
				
				System.out.println("After session.clear()");
				displayPersistenceContext(em);
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		em.close();	// session is inactive
		
		EMFACTORY.close();
		System.out.println("End");
    }
    
    private static void pessimisticForceIncrement(){
    	EntityManager em = EMFACTORY.createEntityManager();
    	Session session = (Session)em.getDelegate();
		try {
			   em.getTransaction().begin();
			   
			   Job job;
//			   job = em.find(Job.class, "FI_MGR", LockModeType.PESSIMISTIC_FORCE_INCREMENT);		//update version even non-dirty and select version
			   job = (Job)session.load(Job.class, "FI_MGR", LockMode.PESSIMISTIC_FORCE_INCREMENT);
			   job.setMaxSalary(job.getMaxSalary().subtract(BigDecimal.valueOf(1)));
			   
			   System.out.println("Commit");
			   em.getTransaction().commit();				//dirty check for update sql
			   
				//flush for transactional write behind
				displayPersistenceContext(em);				//sql in queue already removed 
									//PO is sync but still in cached
				//That means session can be still be used to next transaction but non-thread-safe
				
				System.out.println(job);
				
				em.clear();
				System.out.println("After session.clear()");
				displayPersistenceContext(em);
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		em.close();	// session is inactive
		
		EMFACTORY.close();
		System.out.println("End");
    }
    
    private static void handleVersion(){
    	EntityManager em = EMFACTORY.createEntityManager();
    	Session session = (Session)em.getDelegate();
		try {
			   em.getTransaction().begin();
			   
			   Job job;
//			   job = (Job)session.load(Job.class, "FI_MGR", LockMode.NONE);
//			   job = (Job)session.load(Job.class, "FI_MGR", LockMode.OPTIMISTIC);
//			   job = (Job)session.load(Job.class, "FI_MGR", LockMode.OPTIMISTIC_FORCE_INCREMENT);
//			   job = (Job)session.load(Job.class, "FI_MGR", LockMode.UPGRADE_NOWAIT);
			   
//			   job = em.find(Job.class, "FI_MGR");
//			   em.lock(job, LockModeType.PESSIMISTIC_READ);
//			   job = em.find(Job.class, "FI_MGR", LockModeType.NONE);
//			   job = em.find(Job.class, "FI_MGR", LockModeType.OPTIMISTIC);						//update version (+1) and select version
//			   job = em.find(Job.class, "FI_MGR", LockModeType.OPTIMISTIC_FORCE_INCREMENT);		//update version even non-dirty and select version
			   job = em.find(Job.class, "FI_MGR", LockModeType.PESSIMISTIC_READ);				//where job0_.JOB_ID=? for update
//			   job = em.find(Job.class, "FI_MGR", LockModeType.PESSIMISTIC_WRITE);				//where job0_.JOB_ID=? for update
//			   job = em.find(Job.class, "FI_MGR", LockModeType.PESSIMISTIC_FORCE_INCREMENT);	//update version even non-dirty and where job0_.JOB_ID=? for update nowait
			   
			   em.lock(job, LockModeType.NONE);	//not work for release lock ??
			   
			   Query q = em.createQuery(SELECT_JPQL);		//like JDBC: conn.createPreparedStatement()
			   q.setLockMode(LockModeType.PESSIMISTIC_READ);
			   q.setParameter("minSalary", BigDecimal.valueOf(6000));	//Input Parameters
			   List<Job> list = q.getResultList();				//select sql
			   
			   TypedQuery<Job> typedQuery = em.createNamedQuery("findJobByJobTitle", Job.class);
			   typedQuery.setLockMode(LockModeType.PESSIMISTIC_READ);
			   typedQuery.setParameter("jobTitle", "Finance Manager");
			   List<Job> jobList = typedQuery.getResultList();
			   System.out.println(jobList);
			   
			   job.setMaxSalary(job.getMaxSalary().subtract(BigDecimal.valueOf(1)));
			   System.out.println(job);
			   
			   em.refresh(job, LockModeType.PESSIMISTIC_READ);
			   
			   System.out.println("Commit");
			   em.getTransaction().commit();				//dirty check for update sql
			   
				//flush for transactional write behind
				displayPersistenceContext(em);				//sql in queue already removed 
									//PO is sync but still in cached
				//That means session can be still be used to next transaction but non-thread-safe
				
				System.out.println(job);
				
				em.clear();
				System.out.println("After session.clear()");
				displayPersistenceContext(em);
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		em.close();	// session is inactive
		
		EMFACTORY.close();
		System.out.println("End");
    }
    private static void handleStaff(){
    	EntityManager em = EMFACTORY.createEntityManager();
    	Session session = (Session)em.getDelegate();
		try {
			   em.getTransaction().begin();
			   //-----------Only one table contain all fields--------
			   //Pros: de-normalization for good performance
			   //Cons: dummy value, confused relationship
/*			   from
		        HR.STAFF staff0_ 
		       where
		        staff0_.SID=?*/
			   Staff staff = em.find(Staff.class, 3L);
			   System.out.println(staff);
			   
/*			   select
		        teacher0_.SID as SID3_0_,
		        teacher0_.SNAME as SNAME3_0_,
		        teacher0_.QUALIFICATION as QUALIFIC4_3_0_,
		        teacher0_.SUBJECT as SUBJECT3_0_ 
		    from
		        HR.STAFF teacher0_ 
		    where
		        teacher0_.SID=? 
		        and teacher0_.TYPE='T'*/
			   Teacher teacher = em.find(Teacher.class, 1L);
			   System.out.println(teacher);
			   
			   Expert expert = em.find(Expert.class, 2L);
			   System.out.println(expert);
			   
			   //-------------Join(1 superClass, 2 subClass)-------
			   //Pros: better normalization for one to zero/one, no dummy value
			   //Cons: joining - performance problem
/*			   select
		        warrior0_.S_UID as S1_4_0_,
		        warrior0_.NAME as NAME4_0_,
		        warrior0_1_.POWER as POWER5_0_,
		        warrior0_1_.SKILL as SKILL5_0_,
		        case 
		            when warrior0_1_.S_UID is not null then 1 <-- class definition
		            when warrior0_.S_UID is not null then 0 
		        end as clazz_0_ 
			   HR.WARRIOR warrior0_ 
			    left outer join		<--- SUPERMAN could be empty here
			        HR.SUPERMAN warrior0_1*/
			   Warrior warrior = em.find(Warrior.class, 0L);
			   System.out.println(warrior);
			   
/*			     HR.SUPERMAN superman0_ 
			     inner join			<---
			         HR.WARRIOR superman0_1_ */
			   Superman superman = em.find(Superman.class, 1L);
			   System.out.println(superman);
			   
			   
			   //------------ one table per class-----------------
			   //Pros: NO dummy value for a non-related field for superClass, better performance
			   //Cons: confused relationship 
/*			   from					<-- Union all related table to find out correspond record and class
		        ( select
		            C_UID,
		            NAME,
		            null as CLIMB,
		            null as JUMP,
		            0 as clazz_ 
		        from
		            HR.CREATURE 
		        union
		        all select
		            C_UID,
		            NAME,
		            CLIMB,
		            null as JUMP,
		            1 as clazz_ 
		        from
		            HR.MONKEY 
		        union
		        all select
		            C_UID,
		            NAME,
		            null as CLIMB,
		            JUMP,
		            2 as clazz_ 
		        from
		            HR.MOUSE 
		    ) creature0_ 
		where
		    creature0_.C_UID=?*/
			   Creature creature = em.find(Creature.class, 3L);
			   System.out.println(creature);
			   
			   Creature creatureMonkey = em.find(Creature.class, 2L);
			   System.out.println(creatureMonkey);
			   
/*			   from
		        HR.MONKEY monkey0_ 		<-- specific table 
		    where					
		        monkey0_.C_UID=?*/
			   Monkey monkey = em.find(Monkey.class, 1L);
			   System.out.println(monkey);
			   
			   Mouse mouse = em.find(Mouse.class, 2L);
			   System.out.println(mouse);
			   
			   em.getTransaction().commit();				//dirty check for update sql
			   
				//flush for transactional write behind
				displayPersistenceContext(em);				//sql in queue already removed 
									//PO is sync but still in cached
				//That means session can be still be used to next transaction but non-thread-safe
				
				em.clear();
				System.out.println("After session.clear()");
				displayPersistenceContext(em);
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		em.close();	// session is inactive
		
		EMFACTORY.close();
		System.out.println("End");
    }
    
    
    private static void handleDpt(){
    	EntityManager em = EMFACTORY.createEntityManager();
    	Session session = (Session)em.getDelegate();
		try {
			   em.getTransaction().begin();
			   
			   System.out.println("-----------------Employee------------------------");
			   Employee persistedEmp = em.find(Employee.class, 200L);
			   Department dpt = persistedEmp.getDepartment();	//only get but not access internal field
			   System.out.println("persistedEmp.getDepartment()!=null => " + (persistedEmp.getDepartment()!=null));
			   System.out.println("but load content on use");
			   System.out.println(dpt); 						//first access its field , select Department
			   showEmpInDp(dpt.getEmployees());
			   System.out.println(persistedEmp.getManager());
			   System.out.println(persistedEmp.getManager().getDepartment());
			   showEmpInDp(persistedEmp.getManager().getDepartment().getEmployees());
			   System.out.println(persistedEmp.getManager().getManager());
			   System.out.println(persistedEmp.getManager().getManager().getManager());
			   System.out.println(persistedEmp); 
			   displayPersistenceContext(em);
			   
/*			   System.out.println("-----------------Hibernate------------------------");
			   System.out.println("--------lock()---------");
			   session.evict(persistedEmp);
			   displayPersistenceContext(em);
			   String s = persistedEmp.getFirstName();
			   persistedEmp.setFirstName("HIHI");	
			   session.lock(persistedEmp, LockMode.NONE);	//Re-attach: direct use data as cache without force update
			   												//Even there is change compared with db record during re-attach
			   												//no update on it
			   												//After re-attach, dirty check on directly used data but not more updated one (by select sql)
			   //persistedEmp.setFirstName(s);
			   session.flush();
			   displayPersistenceContext(em);
			   
			   System.out.println("--------update()---------");
			   session.evict(persistedEmp);
			   displayPersistenceContext(em);
			   session.update(persistedEmp);	//Re-attach: force updated data as cache
			   									//"No dirty checking"
			   									//##always treats the object as dirty## and schedules an SQL UPDATE., which will be executed during flush.
			   									//To avoid "No dirty checking"
			   										//- select-before-update='true' 
			   										//- determines whether the object is dirty by executing a SELECT statement and comparing the object's current state to the current database state.
			   session.flush();
			   displayPersistenceContext(em);
			   
			   System.out.println("--------lock()---------");
			   session.evict(persistedEmp);
			   displayPersistenceContext(em);
			   session.delete(persistedEmp);	//remove the detached object from db
			   session.flush();
			   displayPersistenceContext(em);
			   System.out.println("-----------------end------------------------");*/
			   
			   try {
				em.lock(persistedEmp, LockModeType.NONE);
				} catch (IllegalArgumentException e) {
					System.out.println("ava.lang.IllegalArgumentException: entity not in the persistence context");
				}
			   
			   System.out.println("-----------------Department------------------------");
			   
			   Department persistedDpt = em.find(Department.class, 10L);	//read DPT -> list<EMP> through context 
			   System.out.println(persistedDpt);
			   
			   em.getTransaction().commit();				//dirty check for update sql
			   
				//flush for transactional write behind
				displayPersistenceContext(em);				//sql in queue already removed 
									//PO is sync but still in cached
				//That means session can be still be used to next transaction but non-thread-safe
				
				em.clear();
				System.out.println("After session.clear()");
				displayPersistenceContext(em);
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
		}
		
		em.close();	// session is inactive
		
		EMFACTORY.close();
		System.out.println("End");
    }
    
    private static void showEmpInDp(List<Employee> list){
    	Set<Employee> set = new HashSet<Employee>();
    	for (Employee employee : list) {
//    		set.add(employee.getManager());
		}
    	System.out.println(set);
    }
	/**
	 * 
	 */
	private static void handleEmp() {
		EntityManager em = EMFACTORY.createEntityManager();
		try {
			   em.getTransaction().begin();
			   
			   //select
			   Employee e1 = em.find(Employee.class, 100L);
			   
			   System.out.println("----------------manual refresh-------------------");
			   try {
				   em.refresh(e1);
				   System.out.println(e1==em.find(Employee.class, 100L));	//true
				} catch (IllegalArgumentException e) {
					 System.out.println("IllegalArgumentException - e1 is not an entity or the entity is not managed");
				}
			   
			   Employee e2 = em.find(Employee.class, 100L);	//read through 
			   Employee e3 = em.find(Employee.class, 107L);
			   System.out.println("e1==e2 : " + (e1==e2));
			   System.out.println(e1);
			   displayPersistenceContext(em);
			   
			   //update
			   e1.setPhoneNumber(new Date().toString().substring(15, 20));
			   System.out.println(e1);
			   displayPersistenceContext(em);
			   em.detach(e1);
			   System.out.println("After session.detach(e1)");
			   displayPersistenceContext(em);
			   
			   //manual flush
			   System.out.println("----------------manual flush-------------------");
			   em.flush();
			   
			   //Insert
			   Job newJob = new Job();
			   newJob.setUid("JPA3");
			   newJob.setJobTitle("JPA3");
			   System.out.println("----------------manual refresh-------------------");
			   try {
				   em.refresh(newJob);
				} catch (IllegalArgumentException e) {
					 System.out.println("IllegalArgumentException - newJob is not an entity or the entity is not managed");
				}
			   displayPersistenceContext(em);
//			   session.persist(newJob);							//insert sql in queue
			   System.out.println("----------------persist by merge()-------------------");
			   Job persistedPo = em.merge(newJob);		//insert sql if select has no record.
			   System.out.println("mergedNewJob==newJob" + (persistedPo==newJob));
			   newJob = persistedPo;
			   System.out.println("----------------persist end-------------------");
			   try {
				   //Refresh the state of the instance from the database, overwriting changes made to the entity, if any.
				   em.refresh(newJob);
				} catch (PersistenceException e) {
					 System.out.println("HibernateException - newJob does not yet exist as a row in the database");
				}
			   displayPersistenceContext(em);
			   Job newJob2 = em.find(Job.class, "JPA3");	//read through persistence Context by identifier
			   													//Although it is uncommit read, it is find() method , JPQL select
			   													//, no flush at this point
			   newJob.setMaxSalary(BigDecimal.valueOf(123));	//update sql if dirty
			   newJob.setMinSalary(BigDecimal.valueOf(6000));
			   System.out.println(newJob2);
			   displayPersistenceContext(em);
			   
			   //JPQL select - @No read through Feature@
			   //Dynamic JPQL
			   Query q = em.createQuery(SELECT_JPQL);		//like JDBC: conn.createPreparedStatement()
			   q.setParameter("minSalary", BigDecimal.valueOf(6000));	//Input Parameters
			   List<Job> list = q.getResultList();				//select sql
			   													//uncommit read , so flush all POs (why all, i.e it may trigger store-procedure to update Job records)
			   System.out.println("list.contains(newJob2) : " + list.contains(newJob2)); 	//still within identity-scope
			   displayPersistenceContext(em);
			   
			   System.out.println(newJob2);
			   Job newJob3 = em.find(Job.class, "JPA3");	//read through persistence Context by identifier
			   System.out.println("newJob3==newJob2 : " + (newJob3==newJob2));				//still within identity-scope
			   displayPersistenceContext(em);
			   
			   //NamedQuery
			   TypedQuery<Job> typedQuery = em.createNamedQuery("findJobByJobTitle", Job.class);
			   typedQuery.setParameter("jobTitle", "JPA3");
			   List<Job> jobList = typedQuery.getResultList();
			   System.out.println(jobList);
			   displayPersistenceContext(em);
			   
			   //CriteriaQuery
			   CriteriaBuilder cbuilder = em.getCriteriaBuilder();
			   CriteriaQuery<Job> cQuery = cbuilder.createQuery(Job.class);
			   Root<Job> r = cQuery.from(Job.class);
			   cQuery.select(r).where(cbuilder.equal(r.get("uid"), "JPA3"));
			   TypedQuery<Job> typedCq = em.createQuery(cQuery);
			   typedCq.setMaxResults(5);
			   typedCq.setHint("javax.persistence.query.timeout", 2000);
			   List<Job> jobList2 = typedCq.getResultList();
			   System.out.println(jobList2);
			   displayPersistenceContext(em);
			   //--------------------------End----------------------------------
			   
			   //Sequence Generator takes effect on persists()
			   Employee newE = new Employee();
			   newE.setFirstName("raymond");
			   newE.setLastName("wong");
			   newE.setEmail("abc" + new Date().toString().substring(15, 20));
			   newE.setDepartment(e1.getDepartment());
//			   newE.setManager(e1.getManager());
			   newE.setJobId(e1.getJobId());
			   newE.setPhoneNumber("123423");
			   newE.setHireDate(new Date());
			   System.out.println("before session.persist(newE);");
			   System.out.println(newE);
			   em.persist(newE);							//SELECT EMPLOYEES_SEQ.nextval FROM dual;
			   System.out.println("After session.persist(newE);");
			   System.out.println(newE);
			   displayPersistenceContext(em);
			   
			   System.out.println("---------- Re-attach dettached Po(with identifier) by merge() ------------");
			   em.detach(e1);	//detach  PO from context
			   displayPersistenceContext(em);
			   e1.setLastName("AAA");	//update detached PO
			   Employee reattachedPo = em.merge(e1);		//re-attach: 
			   							//1. select  from DB
			   							//2. merge old(DB) with new(dettached)
			   							//3. dettached to persistent
			   System.out.println("----------Re-attach end ------------");
			   
			   
			   //delete
			   em.remove(newJob);							//delete sql in queue
			   em.remove(newE);
			   displayPersistenceContext(em);
			   
			   em.getTransaction().commit();				//dirty check for update sql
			   													//flush for transactional write behind
			   displayPersistenceContext(em);				//sql in queue already removed 
			   													//PO is sync but still in cached
			  //That means session can be still be used to next transaction but non-thread-safe
		
			   em.clear();
			   System.out.println("After session.clear()");
			   displayPersistenceContext(em);
			   
		} catch (Exception e) {
				e.printStackTrace();
				em.getTransaction().rollback();
		}
		
		em.close();	// session is inactive
		
		EMFACTORY.close();
		System.out.println("End");
	}
    
    private static void displayPersistenceContext(EntityManager em){
    	Session session = (Session)em.getDelegate();
    	System.out.println(session);
    	System.out.println(session.getSessionFactory().getStatistics().getSecondLevelCacheStatistics("com.mine.project.Job"));
    	System.out.println(session.getSessionFactory().getStatistics().getSecondLevelCacheStatistics("com.mine.project.Job").getEntries());
    	System.out.println(session.getSessionFactory().getStatistics().getSecondLevelCacheStatistics("query.findJobList"));
    }
}
