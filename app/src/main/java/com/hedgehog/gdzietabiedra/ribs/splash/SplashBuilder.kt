package com.hedgehog.gdzietabiedra.ribs.splash

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hedgehog.gdzietabiedra.R
import com.karumi.dexter.DexterBuilder
import com.uber.rib.core.InteractorBaseComponent
import com.uber.rib.core.ViewBuilder
import dagger.Binds
import dagger.BindsInstance
import dagger.Provides
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy.CLASS
import javax.inject.Qualifier
import javax.inject.Scope

/**
 * Builder for the {@link SplashScope}.
 *
 * TODO describe this scope's responsibility as a whole.
 */
class SplashBuilder(
    dependency: ParentComponent) : ViewBuilder<SplashView, SplashRouter, SplashBuilder.ParentComponent>(
    dependency) {

  /**
   * Builds a new [SplashRouter].
   *
   * @param parentViewGroup parent view group that this router's view will be added to.
   * @return a new [SplashRouter].
   */
  fun build(parentViewGroup: ViewGroup): SplashRouter {
    val view = createView(parentViewGroup)
    val interactor = SplashInteractor()
    val component = DaggerSplashBuilder_Component.builder()
        .parentComponent(dependency)
        .view(view)
        .interactor(interactor)
        .build()
    return component.splashRouter()
  }

  override fun inflateView(inflater: LayoutInflater, parentViewGroup: ViewGroup): SplashView? {
    return inflater.inflate(R.layout.rib_splashscreen, parentViewGroup, false) as SplashView
  }

  interface ParentComponent {
    fun dexter(): DexterBuilder.Permission
    fun splashListener(): SplashListener
  }

  @dagger.Module
  abstract class Module {

    @SplashScope
    @Binds
    internal abstract fun presenter(view: SplashView): SplashInteractor.SplashPresenter

    @dagger.Module
    companion object {

      @SplashScope
      @Provides
      @JvmStatic
      internal fun router(
          component: Component,
          view: SplashView,
          interactor: SplashInteractor): SplashRouter {
        return SplashRouter(view, interactor, component)
      }
    }

    // TODO: Create provider methods for dependencies created by this Rib. These should be static.
  }

  @SplashScope
  @dagger.Component(modules = arrayOf(Module::class),
      dependencies = arrayOf(ParentComponent::class))
  interface Component : InteractorBaseComponent<SplashInteractor>, BuilderComponent {

    @dagger.Component.Builder
    interface Builder {
      @BindsInstance
      fun interactor(interactor: SplashInteractor): Builder

      @BindsInstance
      fun view(view: SplashView): Builder

      fun parentComponent(component: ParentComponent): Builder
      fun build(): Component
    }
  }

  interface BuilderComponent {
    fun splashRouter(): SplashRouter
  }

  @Scope
  @Retention(CLASS)
  internal annotation class SplashScope

  @Qualifier
  @Retention(CLASS)
  internal annotation class SplashInternal
}
