# Mockito Made Clear

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

### 1.2 - Формальное описание процесса
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