
For more details about Epnoi see the documentation: https://github.com/fitash/epnoi/wiki

# EUIA Component

The *Epnoi Unified Information Access* (EUIA) Camel Component is used for communicate a Camel Route to Epnoi system.

Maven users will need to add the following dependency to their *pom.xml* for this component:

```xml
<dependency>
    <groupId>es.upm.oeg.camel</groupId>
    <artifactId>camel-euia</artifactId>
    <version>x.x.x</version>
</dependency>
```
**Note**: The component currently only supports producing (publish) resources.

**Note**: You must include this repository in your *pom.xml*:

```xml
<repositories>
     <!-- GitHub Repository -->
     <repository>
         <id>camel-euia-mvn-repo</id>
         <url>https://raw.github.com/cabadol/camel-euia/mvn-repo/</url>
         <snapshots>
             <enabled>true</enabled>
             <updatePolicy>always</updatePolicy>
         </snapshots>
     </repository>
 </repositories>
```

## URI Format

```
euia:euiaURI
```

Where `euiaURI` is the URI to the Epnoi service.

You can append query options to the URI in the following format, `?option=value&option=value&...`

## Options

## Options
| Property | Default  | Description |
| :------- |:--------:| :---------- |
| servers    | 127.0.0.1    | Comma-separated values of Epnoi servers |
| port    | 5672    | Listening port of Epnoi server |
| username    | guest    | Name of user with grants to use the service. |
| password    | guest    | Password of the user. |
| mode    | batch    | Specifies how this component will communicate with the service. |

## Exchange data types

Camel read the IN body on the Exchange with a message in JSON format.


## EUIA Data Format

The euia component ships with an EUIA dataformat that can be used to convert between `String` (JSON) and `ContextType` model object (Gson).

- `marshal` = from `ContextType` to JSON `String`
- `unmarshal` = from JSON `String` to `ContextType`

A route using this would look something like this:
```
from("direct:start).to(euia://epnoi0.19?servers=10.15.0.19&port=15672");
```


This work is funded by the EC-funded project DrInventor (www.drinventor.eu).
