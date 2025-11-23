package ma.ens.lab7.service;

import java.util.ArrayList;
import java.util.List;

import ma.ens.lab7.bean.Star;
import ma.ens.lab7.dao.IDao;

public class StarService implements IDao<Star> {
    private List<Star> stars;
    private static StarService instance;

    private StarService() {
        stars = new ArrayList<>();
        seed();
    }

    public static StarService getInstance() {
        if (instance == null) instance = new StarService();
        return instance;
    }

    private void seed() {
        stars.add(new Star("Lionel Messi",
                "https://upload.wikimedia.org/wikipedia/commons/7/74/Lionel_Messi_close-up.jpg",
                2f));

        stars.add(new Star("Cristiano Ronaldo",
                "https://upload.wikimedia.org/wikipedia/commons/3/3c/Cristiano_Ronaldo.jpg",
                4f));

        stars.add(new Star("Neymar Jr",
                "https://upload.wikimedia.org/wikipedia/commons/3/31/Neymar_Jr_Portrait_(149008871).jpeg",
                3f));

        stars.add(new Star("Kylian Mbappé",
                "https://upload.wikimedia.org/wikipedia/commons/4/45/Kylian_Mbapp%C3%A9.jpg",
                3f));
    }



    @Override public boolean create(Star o) { return stars.add(o); }
    @Override public boolean update(Star o) {
        for (Star s : stars) {
            if (s.getId() == o.getId()) {
                s.setName(o.getName());
                s.setImg(o.getImg());
                s.setRating(o.getRating());
                return true;
            }
        }
        return false;
    }
    @Override public boolean delete(Star o) { return stars.remove(o); }
    @Override public Star findById(int id) {
        for (Star s : stars) if (s.getId() == id) return s;
        return null;
    }
    @Override public List<Star> findAll() { return stars; }
}
