package com.sammy.malum.core;

import com.bawnorton.mixinsquared.api.MixinCanceller;

import java.util.List;

public class MalumMixinDestroyer implements MixinCanceller {

    @Override
    public boolean shouldCancel(List<String> targetClassNames, String mixinClassName) {
        //This should be removed when portinglib merges the NPE fix PR in this mixin
        if (mixinClassName.equals("io.github.fabricators_of_create.porting_lib.entity.mixin.common.ProjectileUtilMixin")) {
            return true;
        }
        return false;
    }
}