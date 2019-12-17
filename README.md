# Scoco [![Build Status](https://travis-ci.org/St4B/Scoco.svg?branch=master)](https://travis-ci.org/St4B/Scoco)
Scope of a Coroutine, useful for unit-testing. 

We could say that is a light-weight version of https://github.com/Kotlin/kotlinx.coroutines/tree/master/kotlinx-coroutines-test

Especially, with this library we can test coroutines that execute jobs based on time, by ignoring any possible delay or by controlling the time.

### Ignoring delays

The simplest way to test coroutines, that contain delay, is just to ignore the delay. This is useful whenever there is not any need to orchestrate different jobs. What we actually want is to get a result of a long running task. In that case, we can just use the CoroutineScope that is provided from this library and the jobs are going to be executed instantly.

```Kotlin
class Task(private val scope: CoroutineScope) { ... }

class TaskTest {

  private val scope = Scope()
  
  private val task = Task(scope)

  @Test
  fun `should test task`() {
      //Test without caring about the delay
  }

}
```

### Controlling time

If we need to test the orchestration of different jobs that may have delay, then we need to take a different approach. In that case we need to inject in our scope an ContinuationScheduler which is going to be orchestrated by a TimeController. 

#### ContinuationSchedulers

A ContinuationScheduler is responsible for scheduling jobs based on time that is provided from a time controller. The schedulers that are provided for now are:

- DelayedContinuationScheduler, which respects delays
- InstantScheduler, which executes jobs asap, ignoring time controllers 
- Also, you can implement ContinuationScheduler to come up with a custom implementation.

#### Time Controllers

A time controller provide us with the ability to manipulate time inside a CoroutineScope. The time controllers that are provided for now are:

- ForwardTimeController, which respects time's linearity
- BounceTimeController, which can create worm loops inside our coroutine world :p
- Also, you can extend base TimeController to come up with a custom implementation.

The following example demonstrates how to control time in order to test coroutines.

```Kotlin
class Task(private val scope: CoroutineScope) { ... }

class TaskTest {
  
  private val scheduler = DelayedContinuationScheduler()

  private val timeController = ForwardTimeController(scheduler)

  private val scope = Scope(scheduler)
  
  private val task = Task(scope)

  @Test
  fun `should test task`() {
      timeController.forwardBy(1000L)
      //Verify that task behaved as expected after moving forward by 1000 millis
  }

}
```

Dowload
--------

```groovy
dependencies {
  testImplementation "com.quadible:scoco:1.0.0-alpha"
}
```

## License
```
 Copyright 2019 Quadible Ltd.
 
 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at
 
   http://www.apache.org/licenses/LICENSE-2.0
 
 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
```

