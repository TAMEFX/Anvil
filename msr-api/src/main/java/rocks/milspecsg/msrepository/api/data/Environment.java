/*
 *     MSRepository - MilSpecSG
 *     Copyright (C) 2019 Cableguy20
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package rocks.milspecsg.msrepository.api.data;

import com.google.inject.Binding;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import rocks.milspecsg.msrepository.api.data.registry.Registry;
import rocks.milspecsg.msrepository.api.plugin.PluginInfo;

import java.util.Objects;

public interface Environment {

    @SuppressWarnings("unchecked")
    static <T> Binding<T> getBinding(String name, Injector injector) {
        Binding<?>[] binding = {null};
        injector.getBindings().forEach((k, v) -> {
            if (k.getTypeLiteral().getType().getTypeName().contains(name)) {
                binding[0] = v;
            }
        });
        return Objects.requireNonNull(
            (Binding<T>) binding[0],
            "Could not find binding for service: " + name + " in injector " + injector
        );
    }

    static <T> Key<T> getKey(String name, Injector injector) {
        return Environment.<T>getBinding(name, injector).getKey();
    }

    static <T> Provider<T> getProvider(String name, Injector injector) {
        return Environment.<T>getBinding(name, injector).getProvider();
    }

    static <T> T getInstance(String name, Injector injector) {
        return Environment.<T>getProvider(name, injector).get();
    }

    default <T> Binding<T> getBinding(String name) {
        return getBinding(name, getInjector());
    }

    default <T> Key<T> getKey(String name) {
        return getKey(name, getInjector());
    }

    default <T> Provider<T> getProvider(String name) {
        return getProvider(name, getInjector());
    }

    default <T> T getInstance(String name) {
        return getInstance(name, getInjector());
    }

    String getName();

    Injector getInjector();

    <TString> PluginInfo<TString> getPluginInfo();

    Registry getRegistry();
}
