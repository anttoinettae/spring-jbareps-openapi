package com.anttoinettae.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class HibernateManager {
    private SessionFactory sessionFactory;

    public HibernateManager(){
        this.sessionFactory = new Configuration()
                .configure()
                .addAnnotatedClass(HibernateCat.class)
                .addAnnotatedClass(HibernateOwner.class)
                .buildSessionFactory();
    }

    public HibernateCat save(HibernateCat cat){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(cat);
            transaction.commit();
        }
        return cat;
    }

    public HibernateOwner save(HibernateOwner owner){
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(owner);
            transaction.commit();
        }
        return owner;
    }

    public void deleteCatById(long id){
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            HibernateCat cat = getCatById(id);
            session.remove(cat);
            transaction.commit();
        }
    }

    public void deleteOwnerById(long id){
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            HibernateOwner owner = getOwnerById(id);
            session.remove(owner);
            transaction.commit();
        }
    }

    public void deleteByEntity(HibernateCat cat){
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(cat);
            transaction.commit();
        }
    }

    public void deleteByEntity(HibernateOwner owner){
        try (Session session = sessionFactory.openSession()){
            Transaction transaction = session.beginTransaction();
            session.remove(owner);
            transaction.commit();
        }
    }

    public void deleteAll(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Query query = session.createQuery("delete from HibernateCat", HibernateCat.class);
        query.executeUpdate();
        query = session.createQuery("delete from HibernateOwner", HibernateOwner.class);
        query.executeUpdate();
        transaction.commit();
    }

    public HibernateCat update(HibernateCat cat){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        //session.refresh(cat)
        HibernateCat previous = getCatById(cat.getId());
        session.evict(previous);

        previous.setName(cat.getName());
        previous.setOwner(cat.getOwner());

        session.merge(previous);
        transaction.commit();
        return previous;
    }

    public HibernateOwner update(HibernateOwner owner){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        //session.refresh(owner)
        HibernateOwner previous = getOwnerById(owner.getId());
        session.evict(previous);

        previous.setName(owner.getName());

        session.merge(previous);
        transaction.commit();
        return owner;
    }

    public HibernateCat getCatById(long id){
        try (Session session = sessionFactory.openSession()) {
            return session.get(HibernateCat.class, id);
        }
    }

    public HibernateOwner getOwnerById(long id){
        try (Session session = sessionFactory.openSession()) {
            return session.get(HibernateOwner.class, id);
        }
    }

    public List<HibernateCat> getAllCats() {
        try (Session session = sessionFactory.openSession()) {
            Query<HibernateCat> query = session.createQuery("from HibernateCat", HibernateCat.class);
            return query.list();
        }
    }

    public List<HibernateOwner> getAllOwners() {
        try (Session session = sessionFactory.openSession()) {
            Query<HibernateOwner> query = session.createQuery("from HibernateOwner", HibernateOwner.class);
            return query.list();
        }
    }

    public List<HibernateCat> getAllbyOwnerId(long ownerId){
        try (Session session = sessionFactory.openSession()) {
            Query<HibernateCat> query = session.createQuery("from HibernateCat where owner.id = :id", HibernateCat.class);
            query.setParameter("id", ownerId);
            return query.list();
        }
    }

    public void close(){
        sessionFactory.close();
    }
}
