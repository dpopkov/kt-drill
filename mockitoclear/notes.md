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
