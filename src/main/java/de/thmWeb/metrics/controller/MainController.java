package de.thmWeb.metrics.controller;

import de.thmWeb.metrics.config.SwaggerConfig;
import de.thmWeb.metrics.models.ShopLogin;
import de.thmWeb.metrics.models.ShopUser;
import de.thmWeb.metrics.services.LoginService;
import de.thmWeb.metrics.services.ShopUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@Api(tags = SwaggerConfig.TAG_SHOP_USER)
public class MainController {

    private final ShopUserService shopUserService;
    private final LoginService loginService;

    public MainController(ShopUserService shopUserService, LoginService loginService) {
        this.shopUserService = shopUserService;
        this.loginService = loginService;
    }

    @PostMapping("/create")
    @ApiOperation(value = "Create an new shop user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "A new shop user was successfully created")})
    public ResponseEntity<ShopUser> createShopUser(@RequestBody final ShopUser shopUser) {
        final ShopUser createdShopUser = shopUserService.createShopUser(shopUser);

        return new ResponseEntity<>(createdShopUser, OK);
    }

    @PutMapping("/update")
    @ApiOperation(value = "Update an existing shop user")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The shop user was successfully updated"),
            @ApiResponse(code = 404, message = "The shop user could not be updated")})
    public ResponseEntity<ShopUser> updateShopUser(@RequestBody final ShopUser shopUser) {
        final ShopUser updatedShopUser = shopUserService.updateShopUser(shopUser);
        if (updatedShopUser != null) {
            return new ResponseEntity<>(updatedShopUser, OK);
        }

        return new ResponseEntity<>(updatedShopUser, NOT_FOUND);
    }

    @GetMapping("/get-all")
    @ApiOperation(value = "Get a list of all shop users")
    public ResponseEntity<List<ShopUser>> getAllShopUser() {
        final List<ShopUser> allShopUser = shopUserService.getAllShopUser();

        return new ResponseEntity<>(allShopUser, OK);
    }

    @PostMapping("/login")
    @ApiOperation(value = "Login a shop user with its email and password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The shop user could be login"),
            @ApiResponse(code = 404, message = "The shop user could not be login")})
    public ResponseEntity<ShopUser> loginUser(@RequestBody final ShopLogin shopLogin) {
        final ShopUser shopUser = loginService.loginUser(shopLogin, null);
        if (shopUser != null) {
            return new ResponseEntity<>(shopUser, OK);
        }

        return new ResponseEntity<>(shopUser, NOT_FOUND);

    }


    @PostMapping("/login/{sleepTime}")
    @ApiOperation(value = "Login a shop user with its email and password")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "The shop user could be login"),
            @ApiResponse(code = 404, message = "The shop user could not be login")})
    public ResponseEntity<ShopUser> loginUserWithSleepTime(@PathVariable(value = "sleepTime") final String sleepTime, @RequestBody final ShopLogin shopLogin) {
        final ShopUser shopUser = loginService.loginUser(shopLogin, sleepTime);
        if (shopUser != null) {
            return new ResponseEntity<>(shopUser, OK);
        }

        return new ResponseEntity<>(shopUser, NOT_FOUND);

    }

}
