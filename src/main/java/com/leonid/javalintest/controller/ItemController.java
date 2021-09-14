package com.leonid.javalintest.controller;

import com.leonid.javalintest.domain.User;
import io.javalin.apibuilder.CrudHandler;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;

/**
 * @author Leonid Cheremshantsev
 */
public class ItemController implements CrudHandler {

    @Override
    public void create(@NotNull Context ctx) {
        User userObject = ctx.bodyValidator(User.class)
                .check(user -> user.getAge() < 30)
                .get();
        System.out.printf("create %s\n", userObject);
    }

    @Override
    public void delete(@NotNull Context context, @NotNull String s) {
        throw new NullPointerException();
    }

    @Override
    public void getAll(@NotNull Context context) {
        System.out.println("getAll");
        context.result("All items");
    }

    @Override
    public void getOne(@NotNull Context context, @NotNull String s) {
        System.out.println(s);
    }

    @Override
    public void update(@NotNull Context context, @NotNull String s) {

    }
}
