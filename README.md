# Безопасность веб-приложений. Лабораторка №3
## Как запустить:
До всех манипуляций установить IntelliJ IDEA Ultimate, в Comunity версии Spring проект запустить **НЕЛЬЗЯ**

1. Клонировать репозиторий
    * Нажать Get from VCS
  
    ![image](https://user-images.githubusercontent.com/73231157/202687098-b2476cdb-1940-46fb-adb4-e000e672328c.png)
  
    * Вставить ссылку на репозиторий - https://github.com/SirlLizz/websec-3.git
  
    ![image](https://user-images.githubusercontent.com/73231157/202687469-414fe319-6d93-418c-83e4-01f51e0a958d.png)
  
    * Нажать Clone
    
2. Собрать проект
    * Нажать на выпадающий список Project в вкладке Project
    
    ![image](https://user-images.githubusercontent.com/73231157/202688538-98ef0a8d-78f5-4ca3-8e6d-2e2370e4f402.png)

    * Выбрать Project Files
    
    ![image](https://user-images.githubusercontent.com/73231157/202688656-d484a27d-5f62-4d63-bc38-eb530fe6236e.png)

    * Нажать на файл pom.xml правой кнопкой мыши(файл лежит по пути: "server/server/pom.xml") и выбрать пункт Add as Maven Project
    
    ![image](https://user-images.githubusercontent.com/73231157/202689157-f5269763-50b3-45aa-a4c1-ec40d1dbc906.png)
    
3. Добавить npm пакеты
    * В нижнем меню выбрать Terminal
    
    ![image](https://user-images.githubusercontent.com/73231157/202689740-2d265b1d-5f84-43d8-9d4e-56ed304a8bc3.png)

    * В терминал ввести комманду "cd ../../client" (без кавычек)
    * В терминал ввести комманду "npm i" (без кавычек)
    
4. Конфигурация базы данных
    * Для конфигурации файла базы данных application.properties перейдите по пути "server/server/src/main/resources/application.properties" от корневого уровня проекта
    
    ![image](https://user-images.githubusercontent.com/73231157/202693841-3719af87-8567-428d-884c-8605d756860d.png)

    * В нем поменяйте spring.datasource.url, spring.datasource.username, spring.datasource.password на url, username и password для свой базы данных(если испольуете другую базу данных, не PostgreSQL, так же поменяйте spring.datasource.driver-class-name и spring.jpa.database-platform)
    
    ![image](https://user-images.githubusercontent.com/73231157/202694035-f1618542-5bcc-4de3-bf68-63cd540638cf.png)

    
5. Конфигурация для старта Клиента
    * В верхнем правом углу нажать на выпадающее меню и выбрать Edit Configurations...
    
    ![image](https://user-images.githubusercontent.com/73231157/202690512-535a0a9f-abeb-4972-8da5-a7a8fd9a2bb2.png)
    
    * В верхнем левом углу хажать на значак плюса - "+"
    
    ![image](https://user-images.githubusercontent.com/73231157/202690845-aec627ad-61d6-41fb-8e3b-5cbc380b33c8.png)
    
    * Выбрать npm
    
    ![image](https://user-images.githubusercontent.com/73231157/202691321-9c3123fd-a989-4d7f-be70-6265095861fa.png)

    * Сконфигурировать по образцу (package.json лежит по пути "client/package.json" (без кавычек), строка для Enviroment "REACT_APP_DOMAIN_SERVER=http://localhost:8080/;REACT_APP_DOMAIN_SITE=http://localhost:3000/" (без кавычек))
    
    ![image](https://user-images.githubusercontent.com/73231157/202691650-7dbbf4cb-0d40-430b-9b46-f1925b64a8e1.png)
    
    * Нажать Apply
    
6. Запуск проекта
    * В верхнем левом углу в выпадающем меню выбрать ServerApplication 
    
    ![image](https://user-images.githubusercontent.com/73231157/202692232-b89b208d-aaca-4dc5-a326-6cacec23ed88.png)

    * Нажать на зеленый треугольник
    
    ![image](https://user-images.githubusercontent.com/73231157/202692497-d75657ca-3532-41a6-b6fa-fbc2ecef6917.png)

    * Сервер запущен!
    * В верхнем левом углу в выпадающем меню выбрать start
    
    ![image](https://user-images.githubusercontent.com/73231157/202692649-9247107d-a490-43d8-ab34-9e3174da9bcb.png)

    * Нажать на зеленый треугольник
    
    ![image](https://user-images.githubusercontent.com/73231157/202692740-7d1f1d0e-dd1e-4490-a739-f9156dcf9116.png)

    * Клиент запущен!
    * Для остановки надо нажать на красный квадрат и в выпадающем меню выбрать нужный подпункт
    
    ![image](https://user-images.githubusercontent.com/73231157/202693076-5f7f1877-634d-4beb-8468-bfdf10b1a745.png)

    


## Внешний вид
Лента новостей

![image](https://user-images.githubusercontent.com/73231157/200195196-d425c8ba-cfae-40ce-829c-96b4cfbe0bf6.png)


Профиль пользователя

![image](https://user-images.githubusercontent.com/73231157/200195152-d2ad53c8-31b9-42a4-a4a4-7cf8163ae416.png)


Итоговое дерево базы данных

![image](https://user-images.githubusercontent.com/73231157/200194977-002a8a88-460c-4c39-9865-684e00514d98.png)





