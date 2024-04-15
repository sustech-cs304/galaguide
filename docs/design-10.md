[toc]

# Milestone 1    ——    Group 10

> Writer: Lianji Bai        SID: 12110201

## Architectural Design

### Idea Construction

<img src="C:\Users\23098\Desktop\design-10.assets\hand-web.jpg" alt="hand-web" style="zoom: 50%;" />

### Message Management System

#### Overview

This system's architecture is represented through a Use Case UML diagram, which outlines the interactive elements and their respective roles.

#### Use Case UML Diagram

The Use Case UML diagram presented illustrates the two principal actors in the system: the `User` and the `Event-holder`. Each actor interacts with the system through a series of defined use cases, as shown in the diagram.

<img src="C:\Users\23098\Desktop\design-10.assets\IMG_6601.PNG" alt="IMG_6601" style="zoom:67%;" />

#### System Components and Roles

- **Private Chat**: Allows direct messaging between users.
- **Group Chat**: Enables users to communicate within a multi-user environment.
- **Subscribe Event**: Users opt-in to receive updates and notifications about specific events.
- **Subscribe Event-holder**: Users follow event-holders to receive targeted communications from them.
- **Publish Event**: Event-holders can announce new events to the system.
- **Update Event**: Event-holders have the capability to modify details of existing events.

#### Interactions Between Components

- The **Send Message** and **Receive Message** functions are integral to both the Private and Group Chat components, indicating a shared messaging service.
- The **Send Email Notification** is a shared service that integrates with subscription and event publication components, signifying a unified notification system.

#### Purpose of Architecture

The architecture is strategically designed to streamline communication between users and event-holders, leveraging centralized messaging and notification services. This model promotes a modular approach where each component can be developed, maintained, and scaled independently.

#### Explanations of the Architecture

- **Private and Group Chat**: These components are essential for real-time communication. By sharing underlying services, they provide a cohesive user experience while maintaining the distinction between personal and community interactions.
- **Subscriptions**: The subscription mechanisms are crucial for user engagement, allowing for automated, personalized updates via the centralized notification system.
- **Event Management**: The Publish and Update Event features empower event-holders to keep their audience informed and engaged with minimal manual intervention.

#### Architectural Choices

- **Flexibility and Scalability**: 
  - The architecture is tailored for adaptability to accommodate growth in user base and event volume, with an emphasis on maintaining system performance as scale increases.

- **Centralized Notification Services**: 
  - A unified notification system is implemented to streamline communications, reducing complexity and ensuring consistent notification delivery.

- **Real-time Interactions**: 
  - The system supports immediate messaging and updates, which are critical for the timely coordination of event-related activities.

- **Database Friendliness**: 
  - The database is designed for efficiency with a focus on fast query processing, ACID compliance for transaction integrity, and scalability to handle growing data volumes without performance degradation. Backup and recovery mechanisms are integral to this design to ensure data resiliency.

---

### Event Management System

#### Overview

In our course project, we’ve constructed a Use Case UML diagram to outline the functional scope of our Event Management System. This diagram serves as an academic exercise to explore the potential interactions between two types of system users: general Users and Organizers.

#### Use Case Diagram Description

![IMG_6602](C:\Users\23098\Desktop\design-10.assets\IMG_6602.PNG)

The UML diagram delineates several key use cases:

- **For the User**:
  - **View Events**: Enables Users to browse available events, representing the primary entry point into the system’s offerings.
  - **Subscribe Events**: Allows Users to follow events for updates, an optional step that builds on the information gathered from viewing events.
  - **Participate Events**: Encourages active User engagement by facilitating participation in events of interest.
- **For the Organizer**:
  - **Publish Events**: Grants Organizers the ability to list new events, initiating the content cycle of the system.
  - **Edit Events**: Provides Organizers with the tool to update event details, ensuring that event information remains current.
  - **Update Events Status**: Enables Organizers to revise the status of events, such as indicating whether an event is upcoming, ongoing, or completed.

Each use case is crafted to represent a distinct functional requirement of the system, with "extend" relationships suggesting how more specific actions can build upon general ones.

#### Architectural Choices

- **Clarity**: The division of functionalities between User and Organizer roles aims to simplify the user interface and make the system more approachable.
- **Modularity**: This architecture helps us to isolate features for development purposes, which could be beneficial for team collaboration and debugging.
- **Adaptability**: The extendable use cases present an architecture that we anticipate could be more easily adapted to changing requirements or additional features.
- **User Journey**: We’ve endeavored to map out a user journey that is logical and enhances user engagement, from browsing to participating in events.

The architecture is conceptualized with an understanding that it must be viable in an academic setting while also showcasing the fundamental principles of software design we are learning. Through this project, we aim to achieve a balance between theoretical knowledge and practical implementation.

---

## UI Design

### Idea Construction

<img src="C:\Users\23098\Desktop\design-10.assets\hand-UI.jpg" alt="hand-UI" style="zoom: 67%;" />

### Functional UI Design

#### Home Page

![image-20240415220103641](C:\Users\23098\Desktop\design-10.assets\image-20240415220103641.png)

##### User-Centric Design Considerations

The interface is crafted with the end-user in mind, ensuring that:
- **Navigation** is intuitive, with clearly labeled buttons guiding the user naturally through the platform's services.
- **Information Display** is prioritized by providing immediate visibility of new content and user-specific schedules.
- **Interactivity** is enhanced with the 'Quick Functions' area, reducing the number of steps a user must take to perform actions, thereby expediting the user experience.
- **Personalization** is evident through the 'My Events & Reservations' section, which reflects the system's responsiveness to individual user needs.

##### Conclusion

The homepage wireframe for the GALAGuide represents our team’s approach to creating a user-friendly event management interface.  This design is a balance between aesthetic simplicity and functional richness, aiming to deliver a pleasant and efficient user experience.  As students, we acknowledge the iterative nature of design and welcome feedback that will assist in refining our project.

---

#### Event Detail Page

![image-20240415220131217](C:\Users\23098\Desktop\design-10.assets\image-20240415220131217.png)

##### Overview

The Event Detail page is a pivotal part of the GALAGuide platform, presenting comprehensive information about individual events.  This section of the report examines the page's UI design, emphasizing its role in enhancing user experience and information accessibility.

##### User Experience Considerations

- **Accessibility**: The UI layout is straightforward, making it easy for users of all proficiency levels to navigate through the event details without confusion.

- **Engagement**: The interactive elements like the bookmarking feature and image gallery are strategically placed to encourage user interaction with the event content.

- **Efficiency**: The placement of the 'Reserve Now' button is optimized to prompt immediate action, thereby streamlining the reservation process.

##### Conclusion

The design of the Event Detail page wireframe is a testament to our team's commitment to creating a user-friendly and informative interface.  By focusing on clear presentation and straightforward navigation, we aim to enhance the users' journey through the GALAGuide platform.  As students learning the ropes of UI/UX design, we strive to balance functionality with simplicity to meet the users' needs effectively.

---

#### UI Design Report for Chat Interface on GALAGuide

![image-20240415220150605](C:\Users\23098\Desktop\design-10.assets\image-20240415220150605.png)

##### Overview

This section of our report focuses on the UI design for the chat interface within the GALAGuide platform. We have developed a wireframe that aims to create an environment conducive to communication and collaboration among users.

##### Enhancing User Experience

- **Simplicity**: The interface is designed to be user-friendly, with clearly divided sections for chat navigation, viewing, and message composition.
  
- **Efficiency**: By positioning the chat list and members list on the periphery, the design enables users to manage conversations and view participant activity without cluttering the main chat area.

- **Familiarity**: The layout takes cues from popular messaging apps to minimize the learning curve and provide a comfortable experience for new users.

##### Conclusion

The chat interface wireframe for the GALAGuide platform is the outcome of our endeavor to balance functionality with usability. The design reflects our understanding that communication is the heart of user engagement on the platform. As we progress through our academic journey in software development, we remain open to feedback and look forward to refining our UI to best meet the needs of our users.

---

### Color Design

![32c9bad2839e5a25453084353b647eaa](C:\Users\23098\Desktop\design-10.assets\32c9bad2839e5a25453084353b647eaa.jpg)

#### Overview

In our version of the Event Center interface, we've employed a color scheme that aims to enhance usability while providing a visually appealing experience. The color choices are deliberate, seeking to evoke a sense of clarity and organization for users navigating through the events.

- **Primary Color (Teal)**: 
  - The header and sidebar prominently feature a teal color, which conveys a sense of creativity and tranquility. This color helps to immediately draw the eye towards navigational elements and important actions.
  
- **Secondary Colors (Shades of Blue)**: 
  - Varied shades of blue for the event categories and event list items create a hierarchical structure within the UI. The lighter shade provides a background that contrasts against the darker text, aiding readability. The darker blue is used to highlight event names, guiding the user's focus to individual listings.
  
- **Accent Color (Purple)**:
  - The purple outline encapsulating the entire interface adds depth and frames the content area of the UI. It's subtle yet effective in distinguishing the content section from the navigation sidebar.

- **Neutral Colors (Whites and Grays)**:
  - The neutral colors provide a clean and minimalistic backdrop that ensures the primary and secondary colors stand out. Text and interactive icons are in a darker shade to maintain optimal contrast and legibility.

#### Reasoning and User Experience

- **Visual Hierarchy**: The use of color helps to create a visual hierarchy that intuitively guides the user through the interface, from global navigation to specific event details.

- **Focus on Content**: By using more vibrant colors for interactive elements and muted tones for content areas, the design emphasizes the importance of the events themselves, allowing users to focus on the details without overwhelming them with bright colors.

- **Accessibility**: The color contrast adheres to WCAG guidelines, ensuring that the interface is usable by individuals with varying degrees.