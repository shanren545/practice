package shanren.cglib;

public class Dao {
    
    
    
    public Dao() {
        super();
        System.out.println("PeopleDao.Dao()");
        update();
    }

    public void update() {
        //select();
        System.out.println("PeopleDao.update()");
    }

    public void select() {
        System.out.println("PeopleDao.select()");
    }
}
