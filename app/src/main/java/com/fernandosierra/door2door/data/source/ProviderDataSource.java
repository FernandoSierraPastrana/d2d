package com.fernandosierra.door2door.data.source;

import com.fernandosierra.door2door.data.model.RProvider;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProviderDataSource extends DataSource<RProvider> {

    @Inject
    ProviderDataSource() {
        super(RProvider.class);
    }

    @Nullable
    @Override
    public String getPrimaryKey() {
        return RProvider.PRIMARY_KEY;
    }
}
