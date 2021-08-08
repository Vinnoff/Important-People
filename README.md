# Important People
This project is a short dev test which consist of displaying a list of random people (which are all important on their way).  
On click, you can display information about an user (display can vary with orientation) and then interact with their data (phone, email and navigation).  
The list is an infinite scroll list  

## Demo
This app support both portrait and landscape orientation, with a separation in landscape mode and an addition in portrait mode  
![Portrait](../media/demo_vertical.gif?raw=true)  
![Landscape](../media/demo_horizontal.gif?raw=true)

## Architecture
![Architecture Design](../media/architecture.png?raw=true)
The app architecture is based on Clean Architecture and provides modules injected with Koin.  
Koin is a dependency injection library that inject dependencies at runtime and is far more understandable than Dagger `(want something ? Use get())`  

The project use coroutines for network and disk calls. Coroutine are launched with `launch{}` for normal processing.  
Dispatcher.IO is used for all network and disk calls and a CoroutineExceptionHandler handle all exceptions happening in this coroutine, allowing to manage a better display to the user than a crash. 

### Presentation Layer
- __MVVM :__ Using Model-View-ViewModel pattern for separation of view and logic,

- __ViewModel :__ The viewModel use liveDatas to make viewState observable to views,

- __LiveData :__ Provide an observable wrapper  lifecycle aware in order to avoid memory leaks and crashes,

- __ViewState :__ Sealed class used to provide a quick overview of the different possibles view state and a quick implementation,

- __Activities :__ Activities only listen to viewModel associate navigation and create fragments in consequences,

- __Fragments :__ Fragments call viewModel functions on user interactions and observe their viewStates,

- __Adapter :__ Adapters are based on ListAdapters and implements DiffUtil in order to check data changes in real time (plus it update asynchronously, which is cool).

### Domain Layer
- __Interactors :__ Domain layer purpose is to own all the business logic and map a response into an entity usable by the application. interactors are use cases reusable and will be instanced each time it is injected with Koin
- __Entities :__ They are responses transformed to be used and understandable by the presentation layer

### Data Layer
- __Repositories :__ Repositories abstract datasource in categories like user or messages for easy understanding and aggregate data from different datasources.
- __RemoteDataSource :__ Wrap a Retrofit interface and handle code response, throw a Success/Error wrapper if there is error handling or simply a nullable response,
- __Retrofit :__ It's a simple REST client that use OkHttp for HTTP Request and Gson for JSON serialization. We can add a cache management with cache control headers. 
- __OkHttp :__ `for HTTP Request`..