package com.jjh.android.game

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides

interface MessageManager {
    fun message(): String
}

class MessageManagerImpl : MessageManager {
    override fun message() = "Message of the day"
}

@Module
open class AppModule {
    @Provides
    fun messageManagerProvider(): MessageManager = MessageManagerImpl()
}

// Definition of a Dagger component to be used by application
@Component(modules = [AppModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    // Classes that can have dependencies injected by this Component
    fun inject(activity: MainActivity)
}