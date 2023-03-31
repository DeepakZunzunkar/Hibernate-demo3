>#### About
This is basic Hibernate partialy xml and Annotaions configuration ` consoled based Application `,implemented using below jar
- hibernate-core -5.6.14
- hibernate-JPA -2.2 
- hibernate-commons-annotations-5.1.2
- MySQl Connector-  5.1.40
- Java SE 1.8
>[doc](https://docs.jboss.org/hibernate/entitymanager/3.5/reference/en/html/architecture.html)

**Topics Covered** 
>`nativeSQl`,`HQL`,`save vs persist`,`update vs merge`,`get vs load`,`clear vs evict vs close`

#### Difference save  vs persist 
> Actually the difference between hibernate save() and persist() methods is depends on generator class we are using.
- If our generator class is assigned, then there is no difference between save() and persist() methods.
  Because generator ‘assigned’ means, as  a programmer we need to give the primary key value to save in the database right 
- In case of other than assigned generator class, suppose if our generator class name is Increment means hibernate 
   itself will assign the primary key id value into the database.
   so in this case if we call save() or persist() method then it will insert the record into the database normally   
- other than assigned generator, hibernate only used to take care the primary key id value remember.
- save() method can return that primary key id value which is generated by hibernate and 
  here return type is serializable we can see it by 
	`long s = session.save(k);  `
  persist() will never give any value back to the client, it return type is void 
  
- persist() is JPA method and available in hibernate like other JPA implementaions
  but save() is native hibernate method , it's  `not` available in other JPA implementaions . 
  
- using save() method we can save object within and outside the trnsaction boundries means work even out of transaction	boundry
- but persist() will not work out of transaction boundary ,it can save object within transaction boundries .
  
- save() operation cascades the associated instances if the association is mapped with ` cascade="save-update"`
- persist() operation cascades the associated instances if the association is mapped with ` cascade="persist"`

#### Get Vs Load
>Both are from Session interface, and we will call them as session.get() & session.load()
 Both will be use for retrieving the object (a row) from the database.
 
 - Get Method return the persistent instance of the given entity class with the given identifier, or null if there is no such persistent instance. 
 	If the instance is already associated with the session, return that instance. This method never returns an uninitialized instance.
 - Get Method fallow Eager loading means it will hit the database immediately and returns the original object.
 - Load also return the persistent instance of the given entity class with the given identifier,assuming that the instance exists .
   This method might return a proxied instance that is initialized on-demand, when a non-identifier method is accessed.
   when we retrieve an instance that non-existence in db then it will throw an error  ObjectNotFoundException

#### Difference between clear(),close(), evict() 
<b> clear() </b>
>When this method get called inside transaction boundry then all objects which are
          currently associate with particular session will be  disconnected/clean or no longer associate with that Session instance.
		  but if second level cache eneabled then it is availble in that area.
Therefore, after calling this method nothing will be performed on persistance layer or DB.
		  
<b> evict() </b>
>Removes the object from the session. This method is used to dissociate/disconnect the specified object from the session.

<b>  close() </b>
>Close session by calling session.close() method, means End the session and releasing the JDBC Connection and clean up.		  

`evict is for to remove particular object from session but clear and close both are used to remove all object from session
  clear just remove or expire or invalided or clear  object from session but session still open here 
  but in case close ,it remove all object from session plus close the session also 
`

#### Update Vs Merge
>Both update() and merge() methods in hibernate are used to convert the object which is in detached state into persistence state.
 update and merge methods will come into picture whenever we loaded the same object again and again .it should be used as per situation.

- Suppose we are dealing with any object in the same session then we should use update() or saveOrUpdate() method.and if you are sure that the session does not contains an already persistent instance with the same identifier,then use update to save the data in db.
- if you want to save your modifications at any time without knowing about the state of an session, then use merge() .
- Suppose we are creating a session and load an employee object. Now object in session cache. If we close the session at this point and we edit state of object and tried to save using update() it will throw exception. To make object persistent we need to open another session. Now we load same object again in current session. So if we want to update present object with previous object changes we have to use merge() method.Merge method will merge changes of both states of object and will save in database.

- within a session if i load record using get or load method of session and and within transaction boundary if make changes in that fetch object
 then that changes will be reflected means update query will get fired if change in the content without using upadte or merge method .





