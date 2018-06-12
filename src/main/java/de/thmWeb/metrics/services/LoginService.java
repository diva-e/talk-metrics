package de.thmWeb.metrics.services;

import com.codahale.metrics.annotation.Timed;
import de.thmWeb.metrics.models.ShopLogin;
import de.thmWeb.metrics.models.ShopUser;
import de.thmWeb.metrics.persistence.ShopUserPersistence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private static final Logger LOG = LoggerFactory.getLogger(LoginService.class);

    private final ShopUserPersistence shopUserPersistence;

    public LoginService(ShopUserPersistence shopUserPersistence) {
        this.shopUserPersistence = shopUserPersistence;
    }


    @Timed
    public ShopUser loginUser(final ShopLogin shopLogin, final String sleepTime) {
        if (sleepTime != null) {

            LOG.info("loginUser sleep for {} milliseconds...", sleepTime);
            try {
                Thread.sleep(Long.parseLong(sleepTime));
            } catch (InterruptedException e) {
                LOG.error(e.getMessage(), e);
            }
        }

        return shopUserPersistence.findByEmailAndPassword(shopLogin.getEmail(), shopLogin.getPassword());
    }


}
