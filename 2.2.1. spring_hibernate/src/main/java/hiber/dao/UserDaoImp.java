package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getUserByCar(Car car) {
/* Выбираем объекты типа `User`, у которых поле `car` имеет заданную модель и серию. Здесь `u` - псевдоним для
   объекта `User`, `u.car.model` - обращение к полю `model` объекта `car`, принадлежащего объекту `u`*/
      String hql = "FROM User u WHERE u.car.model = :model AND u.car.series = :series";
      Query query = sessionFactory.openSession().createQuery(hql);
      query.setParameter("model", car.getModel());
      query.setParameter("series", car.getSeries());

      return (User) query.getResultList().get(0);
   }
}
