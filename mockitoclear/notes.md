# Mockito Made Clear

- 1 - Build a Testing Foundation
  - [1.1 - Get Started with Mockito](notes.md#11---get-started-with-mockito)
  - [1.2 - Formal description of the process](notes.md#12---formal-description-of-the-process)
- 2 - Work with Mockito API
  - [2.1 - Creating Mocks and Stubs](notes.md#21---creating-mocks-and-stubs)
  - [2.2 - Setting Expectations](notes.md#22---setting-expectations)
- 3 - Use Built-in and Custom Matchers
  - [3.1 - Using the Existing Argument Matchers](notes.md#31---using-the-existing-argument-matchers)
  - [3.2 - Creating Custom Argument Matchers](notes.md#32---creating-custom-argument-matchers)
  - [3.3 - Verifying the Order of Methods Called](notes.md#33---verifying-the-order-of-methods-called)
- 4 - Solve Problems with Mockito
  - [4.1 - Deciding between Mockito and BDDMockito](notes.md#41---deciding-between-mockito-and-bddmockito)
  - [4.2 - Testing void Methods Using Interactions](notes.md#42---testing-void-methods-using-interactions)
  - [4.3 - Capturing Arguments](notes.md#43---capturing-arguments)
  - [Using Mockito with Kotlin non-nullable parameters](notes.md#using-mockito-with-kotlin-non-nullable-parameters)
  - [4.4 - Setting Outputs Based on Inputs with Custom Answers](notes.md#44---setting-outputs-based-on-inputs-with-custom-answers)
  - [4.5 - Spying to Verify Interactions](notes.md#45---spying-to-verify-interactions)
- 5 - Use Mockito in Special Cases
  - [5.1 - Mocking Final Classes and Methods](notes.md#51---mocking-final-classes-and-methods)
  - [5.2 - Mocking Static Methods](notes.md#52---mocking-static-methods)
  - [5.3 - Mocking Constructors](notes.md#53---mocking-constructors)
  - [5.4 - Working with Spring Framework](notes.md#54---working-with-spring-framework)

## 1 - Build a Testing Foundation

### 1.1 - Get Started with Mockito
- Добавить Mockito в проект
  - определить зависимости и их версии в `gradle/libs.versions.toml`
  - добавить зависимости в `build.gradle.kts`
- Использовать Mockito в тестах
  - use `@Mock` and `@InjectMocks` annotations
  - use `org.mockito.Mockito.when` чтобы установить ожидания
  - вызвать тестируемый метод
  - use `org.mockito.InOrder` to verify что методы вызваны в правильном порядке

### 1.2 - Formal description of the process
1. Создать stubs для замены зависимостей.
2. Установить ожидания от stubs.
3. Внедрить stubs в тестируемый класс.
4. Вызвать тестируемые методы, которые в свою очередь вызовут методы в stubs.
5. Проверить, что тестируемые методы отработали как ожидалось.
6. Проверить, что методы в зависимостях отработали правильное кол-во раз в правильном порядке.


## 2 - Work with Mockito API

### 2.1 - Creating Mocks and Stubs
- Using the `mock` Method:
  - `publis static <T> T mock(Class<T> classToMock)`
  - `PersonRepository repo = mock(PersonReponsiotry.class);`
  - Mockito генерирует класс, в котором методы возвращают значения по умолчанию.
- Using annotations:
  - `@Mock` - создает экземпляр зависимости
  - `@InjectMocks` - создает тестируемый объект и внедряет зависимости в него
  - для работы аннотаций необходимо поставить `@ExtendWith(MockitoExtension.class)` на класс тестов
  - `MockitoExtension` обеспечивает **strict stubbings**, при котором допускается мокать только действительно выполняемые вызовы.
  - создание моков и их внедрение в тестируемый класс автоматизировано
  - Mockito не является DI фреймворком, и поэтому не должен использоваться для внедрения сложных графов

### 2.2 - Setting Expectations
- Установка ожиданий методами `when` и `thenReturn`, `thenThrow`, `thenAnswer`
  - `publis static <T> OngoingStubbing<T> when(T methodCall)`
  - `OngoingStubbing<T> thenReturn(T value)` - задает результат одного вызова
  - `OngoingStubbing<T> thenReturn(T value, T... values)` - задает результаты нескольких вызовов
  - методы `thenReturn`, `thenThrow`, `thenAnswer` могут вызываться последовательно и в любом порядке
- Проверка Multiplicity
  - `publis static <T> T verify(T mock, org.mockito.verification.VerificationMode mode)`
  - 2-й параметр генерируется методами `times(int), never(), atLeastOnce(), atLeast(int), atMostOnce(), atMost(int)`
  - значением по умолчанию является `times(1)`
- Мокирование методов возвращающих void
  - для методов возвращающих void использование синтаксиса `when/then` невозможно
  - следует использовать `doThrow, doReturn, doAnswer, doNothing, doCallRealMethod`:
    - `doThrow(RuntimeException.class).when(personRepository).delete(null)`
- Проверка вложенных exceptions:
  ```java
  assertThatExceptionOfType(RuntimeException.class)
                  .isThrownBy(() -> service.getAstroData())
                  .withCauseInstanceOf(IOException.class)
                  .withMessageContaining("Network problems");
  ```


## 3 - Use Built-in and Custom Matchers

### 3.1 - Using the Existing Argument Matchers
- Методы типа `anyInt()` могут применяться и при установке ожиданий, и при проверке.
- методы для
  - примитивов: `anyByte, anyShort, anyInt, anyLong, anyFloat, anyDouble, anyChar, anyBoolean`
  - коллекций: `anyCollection, anyList, anySet, anyMap`
  - строк: `anyString, startsWith, endsWith, matches(String), matches(Pattern)`
  - nulls: `isNull, isNotNull, notNull, nullable(Class)`
  - типов: `isA(Class), any(Class)`
  - всего, включая nulls и varargs: `any`
- Using the `eq` Matchers
  - Если вы stubbing метод принимающий более одного аргумента и используете argument matcher для одного из них,
  - то вы **обязаны использовать argument matchers для всех из них**:
    - так нельзя:
      - `verify(mock).someMethod(anyInt(), anyString(), "3rd argument")`
    - нужно так:
      - `verify(mock).someMethod(anyInt(), anyString(), eq("3rd argument"))`
- Дополнительные matchers в классе `AdditionalMatchers`
  - `and, or, not` - для комбинирования
  - `lt, leq, gt, geq` - для сравнения
  - также matchers для сравнения массивов

### 3.2 - Creating Custom Argument Matchers
- `ArgumentMatchers` содержит метод `argThat`, который принимает `ArgumentMatcher`
- `ArgumentMatcher` имеет единственный метод:
  - `boolean matches(T argument)`
- Using Argument Matchers для примитивных типов
  - для примитивов вместо `argThat` следует использовать специализированные методы:
  - `byteThat, shortThat, charThat, intThat, longThat, floatThat, doubleThat, booleanThat`

### 3.3 - Verifying the Order of Methods Called
- `org.mockito.InOrder` позволяет проверить что метод одного стаба вызывался раньше метода другого стаба.
  - `InOrder inOrder = inOrder(firstMock, secondMock);`
  - `inOrder.verify(firstMock).add("was called first");`
  - `inOrder.verify(secondMock).add("was called second");`
  - [example in JHelloMockitoTest](src/test/java/learn/mockito/p1foundation/s1person/JHelloMockitoTest.java)
  - [example in KHelloMockitoTest](src/test/kotlin/p1foundation/s1person/KHelloMockitoTest.kt)


## 4 - Solve Problems with Mockito

### 4.1 - Deciding between Mockito and BDDMockito
- Mockito: when/thenReturn
  - `when(repository.findAll()).thenReturn(people);`
  - `doThrow(RuntimeException.class).when(mock).foo();`
- BDDMockito: given/willReturn
  - `given(repository.findAll()).willReturn(people);`
  - `willThrow(new RuntimeException()).given(mock).foo();`
- Mockito: verify
  - `verify(repository).findAll();`
- BDDMockito: then/should
  - `then(repository).should().findAll();`
  - `then(repository).should(times(1)).findAll();`

### 4.2 - Testing void Methods Using Interactions
- Для проверки взаимодействия для участвующих во взаимодействии компонентов создают моки (метод `mock`).
- Связывание этих компонентов производится вручную, без использования аннотаций.
- Метод `verify` позволяет убедиться, что на моке был вызван нужный метод нужное кол-во раз.
- Метод `argThat` позволяет использовать custom matchers, в том числе с регулярными выражениями.

### 4.3 - Capturing Arguments
- В некоторых случаях входные аргументы могут быть модифицированы или конвертированы перед передачей в другой компонент.
- Если эти данные передаются в качестве аргумента в мокированный метод, то их можно перехватить.
- Для захвата передаваемых данных используется `ArgumentCaptor` с аннотацией `@Captor`:
  - `@Captor private ArgumentCaptor<Person> personArg;`
- Для захвата в момент вызова используется метод `capture`:
  - `verify(repository).save(personArg.capture());`
- Перехваченные данные получают с помощью `getValue()`:
  - `assertThat(personArg.getValue()).isEqualTo(hopper);`

### Using Mockito with Kotlin non-nullable parameters
- Для обхода проблемы с non-nullable параметрами Kotlin приходится использовать доп. функции типа:
  - `fun <T> ArgumentCaptor<T>.captureK(): T = this.capture()`
  - Использовать в случае, если результат capture передается в метод принимающий аргумент non-nullable типа.

### 4.4 - Setting Outputs Based on Inputs with Custom Answers
- Используется интерфейс `Answer`.
- Custom answers позволяют указать как вывод мокированного метода базируется на его вводе.
- Вместо вызова `thenReturn` используется `thenAnswer`.
- Ключевым для данного примера является метод `InvokationOnMock.getArgument(int index)`,
- который возвращает аргумент по заданному индексу, то есть для единственного аргумента индекс будет 0.
- Например, можно вернуть тот же объект, который был передан на входе, вызывая `getArgument(0)`:
  - `when(personRepository.save(any(JPerson.class)))`
  - `    .thenAnswer(invocation -> invocation.getArgument(0));`

### 4.5 - Spying to Verify Interactions
- Spy это объект обертывающий настоящий экземпляр зависимости,
- что позволяет отследить взаимодействия между тестируемым классом и этим компонентом-зависимостью:
  - можно перехватить вызовы для верификации;
  - можно мокировать некоторые методы зависимости вместо всех - это называется partial mock.
- Для заворачивания в spy достаточно использовать метод `Mockito.spy()`:
  - `JPersonRepository repository = spy(new JInMemoryPersonRepository());`
- Проверки проводятся стандартно:
  - `verify(repository, times(people.size())).save(any(JPerson.class));`
- Есть возможность делать partial spy, но это может вызывать проблемы с состоянием.


## 5 - Use Mockito in Special Cases

### 5.1 - Mocking Final Classes and Methods
- До Mockito 5 нужно было либо добавлять `mockito-extensions`, либо заменять `mockito-core` на `mockito-inline`.
- Начиная с Mockito 5 возможность мокировать final интегрирована в Mockito core.
- При использовании с Java 17, Mockito 5 использует inline capability по умолчанию и старые способы не нужны.

### 5.2 - Mocking Static Methods
- Для мокирования статических методов служит `org.mockito.Mockito.mockStatic`,
- который вызывается в try-with-resources блоке:
  - `try(MockedStatic<WikiUtil> mocked = mockStatic(WikiUtil.class))`
- для установки ожиданий используется лямбда:
  - `mocked.when(() -> WikiUtil.getWikipediaExtract(anyString()))`
  - `   .thenAnswer(invocation -> "Bio for " + invocation.getArgument(0));`
- вызов может быть верифицирован:
  - `mocked.verify(() -> WikiUtil.getWikipediaExtract(anyString()), times(3));`

### 5.3 - Mocking Constructors
- Используется `org.mockito. MockedConstruction<T> mockConstruction(classToMock, mockInitializer)`, где
  - classToMock - мокируемый класс, экземпляр которого локально создается внутри мокируемого конструктора,
  - mockInitializer - мокирование его поведения, установка ожиданий, например через Answer.
- Создание экземпляра тестируемого класса производится в try-with-resources блоке
  - при этом мокируемая локальная зависимость, от которой зависит код внутри конструктора, будет создана автоматически.
- Вместо `mockConstruction` может быть также использован метод `mockConstructionWithAnswer`
  - `mockConstructionWithAnswer(ServiceToMock.class, invocation -> invocation.getArgument(0) + " (translated)")`
- Может быть определено несколько Answer, которые будут возвращаться при последовательных обращениях.

### 5.4 - Working with Spring Framework
- Spring имеет поддержку Mockito с самого начала.
- Spring предоставляет аннотацию `@MockBean`, которая:
  - создает mock объект, заменяя собой аннотацию `@Mock`,
  - подставляет этот mock везде в ApplicationContext вместо исходного бина.
- Таким образом `@MockBean` заменяет собой `@Mock` и `@InjectMocks`, так как Spring выполняет DI.
- Ожидания от мока устанавливаются обычным образом, через `when` и т.д.
- Spring также имеет соответствующую аннотацию `@SpyBean`, которая оборачивает существующий компонент.

[Top](notes.md#mockito-made-clear)
