package com.fernandosierra.door2door.data.source;

import com.fernandosierra.door2door.domain.model.Provider;

import org.jetbrains.annotations.Nullable;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProviderDataSource extends DataSource<Provider> {

    @Inject
    public ProviderDataSource() {
        super(Provider.class);
    }

    @Nullable
    @Override
    public String getPrimaryKey() {
        return Provider.PRIMARY_KEY;
    }
}
