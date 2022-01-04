# 선우야 잠 좀 자자


## Introduction
### Abstraction
**선우야 잠 좀 자자** is an Android-application that has 3 tabs: Contact, Gallery, [Unknown]. Overall, **선우야 잠 좀 자자** is implemented with navigation and fragments.

### Development Envrionment
- **IDE**: Android Studio
- **Development Language**: Kotlin
- **Testing Device**
    - Device Model: SM-G930K (Samsung Galaxy S7)
    - Android Version: 8.0.0

---
## Tab Explanation in detail
### **Tab 1. Contacts**   
Tab that retrieves phone's contact list, and the user can search, add contact, call, message here.
* You need autorhization permission: `READ_CONTACTS`, and `CALL_PHONE`
* Retrieving contact list
    - Core function: `getPhoneNumbers(sort: String, searchName: String?): List<ContactItem>`
    - Uses [ContactsContract](https://developer.android.com/reference/android/provider/ContactsContract), [ContentResolver](https://developer.android.com/reference/android/content/ContentResolver), [Cursor](https://www.google.com/search?q=kotlin+cursor&oq=kotlin+cursor&aqs=chrome..69i57.1736j0j4&sourceid=chrome&ie=UTF-8) to retrieve data
    - As return type implies, user data will be wrpaped as `ContactItem` data class.
    - Data taken will be visualized with RecyclerView. For further information, please refer `contact/ContactAdapter.kt`, `contact/ContactFragment.kt`, `res/layout/item_contact.kt`
* Misc
    - The user can call by pressing button
    - The user can message by pressing button
    - The user can add contact by pressing button
    - When the user press the item for a long time, alertDialog will be shown so that user can enjoy activities.
* Demo  
[데모영상]  
<img src="" width="400" height="800">


### **Tab 2. Custom Gallery**
Phone address page with save, delete, and bookmark features.    
* Store names and contact information using SQLite database
* List contact information using RecyclerView
* Implemented Add, Wish, and Delete buttons
* Press the phone icon to switch to dial the number
* Implemented swipe refresh function

<p float="left">
<img src="" width="400" height="800">
<img src="" width="400" height="800">
</p>


### **Tab 3. [Unknown]**    
Tab that you can check how much you ate. [Unknown] is consisted with 2 fragments: *Login fragment* and *Main Fragment*. User can enter foods that they ate, and [Unknown] will visualize how much the user ate compared to user's BMR.
#### Login Fragment
* Get user's physical data that is required for calculating BMR(Basal Metabolic Rate, 기초대사량)
* This information contains: User name, height(cm), weight(kg), age, and sex.
* This information will be wrapped as `Profile` data class, and will be stored as SharedPreferences. This will let users not to input profile information on and on which is quite cumbersome.
    - Originally, this information was passed via bundle, but for I modified it as SharedPreferences to reduce hassle login action.
* For detail, refer `healthcare/LoginFragment.kt`, `res/layout/fragment_healthcare_login.xml`

#### Main Fragment
1. Profile Information
- Used [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart) to visualize how much user ate compared to user BMR as Pie Chart.
- Demo  
[데모영상]

2. Searching 
- Used [식품영양성분DB(NEW)](https://www.foodsafetykorea.go.kr/api/newDatasetDetail.do) that 식품의약품안전처 식품안전정보원 provides. You can get `Food maker name`, `nutrition informaiton`(including serving weight, total calories, carbohydrate, protein, fat, etc...). This information will be wrapped as `FoodData` data class
- This food lists will be visualized by RecyclerView with `HealthAdapter` class.
- If the user provide the weight of food he/she ate, it will adjust calories. If not, it will use Serving per container as default value.
- For code that I use for API communication, please refer `healthcare/ApiFoodInfo.kt`
- For code used for RecylerView, please refer `healthcare/HealthAdapter.kt`, `res/layout/item_food_data.xml`, 
- Demo  
[데모영상]

3. Misc
- User can edit personal physcail profile by pressing button
- User can see how much and what he/she ate during the day by pressing button
    - It will pop up alertDialog with lists
    - When the user press list element, the food user ate will be deleted
- [데모영상]
<p float="left">
<img src="" width="400" height="800">
<img src="" width="400" height="800">
</p>  

- To store data(user profile, food history), I used [gson](https://github.com/google/gson) for data class - string conversion.
- For implementation details, please refer `healthcare/MainFragment.kt`, `healthcare/ApiFoodInfo.kt`, `healthcare/HealthAdapter.kt`




---
## Future Works
### **1. Contacts**
- Get profile picture and visualize it with glide
- Implement profile deletion function
- Make additional fragment to visualize profile detail
- 초성검색 구현 [(Usefu Resource)](https://github.com/javafa/thisiskotlin/tree/master/1st_edition/SearchJaum)
### **2. Custom Gallery**
### **3. [Unknown]**
- Find exercise calories api, and subtarct from the total calories
- Provide detailed nutrition information
- Add animation


---
## Contacts
Contributors
- Woojin, Lee, wjl0209@kaist.ac.kr
- Sunwoo Kim, 선우 이메일
