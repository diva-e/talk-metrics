package de.thmWeb.metrics.persistence;

import de.thmWeb.metrics.models.ShopUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class ShopUserPersistence {

    private final Map<String, ShopUser> shopUserMap = new HashMap<>();


    public void save(final ShopUser shopUser) {
        shopUserMap.put(shopUser.getIdx(), shopUser);
    }

    public List<ShopUser> findAll() {
        return new ArrayList<ShopUser>(shopUserMap.values());
    }

    public ShopUser findByIdx(final String idx) {
        return shopUserMap.get(idx);
    }

    public ShopUser findByEmailAndPassword(final String email, final String password) {
        final List<ShopUser> allShopUsers = findAll();
        for (final ShopUser shopUser : allShopUsers) {
            if (shopUser.getEmail().equals(email) && shopUser.getPassword().equals(password)) {
                return shopUser;
            }
        }
        return null;
    }
}
