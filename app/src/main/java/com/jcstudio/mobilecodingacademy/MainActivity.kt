package com.jcstudio.mobilecodingacademy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jcstudio.mobilecodingacademy.learning.concurrency.presentation.screen.ConcurrencyLabScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
//    private val api = NetworkModule.characterApi
//    private val repository: CharacterRepository = CharacterRepositoryImpl(api)

    /*
    *       Why do we use a ViewModelFactory instead of creating the ViewModel manually inside the Activity?
    *
    *       Because the ViewModel depends on a repository, and the Activity shouldn't be responsible for
    *       constructing the whole dependency graph. A viewModelFactory allows us to inject dependencies
    *       while keeping the viewModel independent  from the Activity. In production, I would normally use Hilt to
    *       handle dependency injection automatically.
    * */
//    private val viewModel: CharacterViewModel by viewModels {
//        CharacterViewModelFactory(repository)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
//            CharacterScreen(
//                viewModel = viewModel
//            )
//            AppNavigation()
            ConcurrencyLabScreen()
        }
    }
}