# <p style="text-align:center">Дипломный проект </p>

## <p style="text-align:center">API-часть на примере сайта-тренажера для API-тестов [petstore](https://petstore.swagger.io/#/)</p>

### <p style="text-align:center">С UI-частью можно ознакомиться по [ссылке](https://github.com/KrestIV/QAGuru_diplom_ui)</p>

# Содержание

- [Стек технологий](#стек-технологий)
- [Список тестов](#список-тестов)
- [Jenkins](#jenkins)
- [Запуск автотестов](#запуск-автотестов)
- [Allure-отчет](#allure-отчеты)
- [Уведомления в телеграм](#уведомления-в-телеграм)

## Стек технологий

<p style="background:#ffffff;text-align:center">
<a href="https://www.java.com/ru/"><img width="8%" title="Java" src="media/java-original.svg" alt="java_logo"></a>
<a href="https://www.jetbrains.com/idea/"><img width="8%" title="IntelliJ IDEA" src="media/intellij-original.svg" alt="intelliJIDEA_logo"></a>
<a href="https://rest-assured.io/"><img width="8%" title="REST-assured" src="media/rest_assured.png" alt="REST-assured_logo"></a>
<a href="https://aerokube.com/selenoid/"><img width="8%" title="Selenoid" src="media/Selenoid.svg" alt="selenoid_logo"></a>
<a href="https://allurereport.org/"><img width="8%" title="Allure Report" src="media/Allure_Report.svg" alt="allure_logo"></a>
<a href="https://gradle.org/"><img width="8%" title="Gradle" src="media/Gradle.svg" alt="gradle_logo"></a>
<a href="https://junit.org/junit5/"><img width="8%" title="JUnit5" src="media/junit-original-wordmark.svg" alt="jUnit5_logo"></a>
<a href="https://github.com/"><img width="8%" title="GitHub" src="media/GitHub.svg" alt="gitHub_logo"></a>
<a href="https://www.jenkins.io/"><img width="8%" title="Jenkins" src="media/Jenkins.svg" alt="jenkins_logo"></a>
<a href="https://telegram.org/"><img width="8%" title="Telegram" src="media/Telegram.svg" alt="telegram_logo"></a>
</p>  

Автотесты в проекте написаны на <code>Java</code> с использованием
библиотеки [REST-assured](https://rest-assured.io/),  
сборщик - <code>Gradle</code>,  
фреймворк модульного тестирования - <code>JUnit 5</code>,  
удаленный запуск браузера и прохождение сценариев - [Selenoid](https://aerokube.com/selenoid/),  
управление удаленным запуском, настройка параметров и формирование отчета - <code>Jenkins + Allure</code>.  
Реализована отправка результатов в <code>Telegram</code> при помощи бота.

### Список тестов

**addingPetMustReturnCorrectAnswer** - Тест создания записи нового питомца в базе магазина  
**gettingPetMustReturnPet** - Тест получения информации о питомце по его id  
**deletingPetMustReturnCorrectResponse** - Тест удаления питомца  
**editPetNameMustSaveInBase** - Тест редактирования клички питомца  
**editThroughPostPetMustSaveInBase** - Тест изменения статуса питомца

### Jenkins

Настройка, запуск и переход к результатам запуска автотестов осуществляется
в [Jenkins](https://jenkins.autotests.cloud/job/007-ilya_krestsov_qa_guru_javaAQA_diplom_API/)

<p>
<img src="media/JenkinsJobMainScreen.png" style="background: #FFFFFF" alt="Jenkins_job">
</p>

### Запуск автотестов

Автотесты запускаются сборкой в <code>Jenkins</code> с заполнением параметров выполнения

<p>
<img src="media/jenkinsBuildOptions.png" style="background: #FFFFFF" alt="jenkins_build_parameters">
</p>  

- **TASK** - Параметр, определяющий набор тестов для запуска
- **PLATFORM** - Платформа для выполнения автотестов
- **SERVER** - Адрес сервера выполнения тестов
- **LOGIN** - Логин для доступа к серверу выполнения тестов
- **PASSWORD** - пароль для доступа к серверу выполнения тестов
- **COMMENT** - Параметр, определяющий текст сообщения в боте уведомлений

### Allure-отчеты

Allure-отчет содержит в себе результаты выполнения всех тестов с полной информацией об отправленных запросах и
полученных на них ответов

[Пример полного allure-отчета](https://jenkins.autotests.cloud/job/007-ilya_krestsov_qa_guru_javaAQA_diplom_API/allure/)

<p>
<img src="media/allureReportExample.png" style="background: #FFFFFF" alt="allure_report">
</p>

### Уведомления в телеграм

По завершении выполнения тестов отправляется краткий отчет в телеграм-бот со ссылкой на полный allure-отчет

<p>
<img src="media/telegramNotification.png" style="background: #FFFFFF" alt="telegram_notification">
</p>

### <p style="text-align:center">С UI-частью можно ознакомиться по [ссылке](https://github.com/KrestIV/QAGuru_diplom_ui)</p>