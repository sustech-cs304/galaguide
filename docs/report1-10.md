[toc]



# GALAGuide — Software Engineering Proposal

> Name: Lianji Bai    SID: 12110201

## Project Overview

### What is GALA Guide?

As a comprehensive campus event assistant platform, we are dedicated to revolutionizing the way events are organized by integrating all essential functionalities needed for event planning and execution. Our platform serves as a seamless conduit for event organizers and participants, significantly reducing communication costs and streamlining the organizational process. Additionally, we offer personalized event recommendations, ensuring students can effortlessly discover activities that resonate with their interests without the hassle of searching amidst their busy academic schedules. Our mission is to enhance the campus experience by facilitating easy access to a diverse array of events, fostering a vibrant and engaged student community.

### Who are our key customers?

Our target users are primarily students, faculty members, and event organizers within the college ecosystem, who seek an efficient, streamlined approach to campus event management and participation. This inclusive platform is designed to cater to a wide range of event types, from academic lectures and workshops to social gatherings and sports events, ensuring a comprehensive service for all campus-related activities.

### What are the functionalities of our GALAGuide?

In summary, our software solution distinctly addresses the needs and challenges faced by participants and organizers within the event landscape. By providing targeted functionalities for each group, we aim to streamline the event discovery, booking, management, and engagement processes, enhancing the overall experience for both participants and organizers.

---



##  Preliminary Requirement Analysis

### Functional requirements & Features

#### Event Center

##### Participator

Participants require an efficient way to discover events that align with their interests and additionally need access to detailed, up-to-date event information, including location and time. Consequently, our software offers an event discovery tool that not only provides search and filter capabilities but also complements these with detailed event information, including maps and scheduling details.

##### Organizer

Organizers are in need of a comprehensive platform for the straightforward posting of events and furthermore the ability to easily modify event details to ensure accurate, timely information for participants. In response, our solution equips organizers with a one-stop platform that facilitates event posting and detail editing, thereby enhancing the ease of event management and participant engagement.

#### Event Booking

##### Participator

Participants express a desire for a streamlined booking process for events, coupled with the ability to view their submitted information and an overview of expected participation levels. Addressing this, a user-friendly event booking system is made available, enabling participants to submit booking information, check their booking status, and view expected participation levels with ease.

##### Organizer

Organizers articulate a need for an efficient way to manage bookings, which includes having an overview of bookings and the capability to conduct batch check-ins. To fulfill this requirement, our platform introduces tools for organizers to view and manage event bookings and execute batch check-ins, thus simplifying the management of participant attendance.

#### Messaging System

##### Participator

Participants highlight the need for a simple and rapid way to contact event organizers and other participants, as well as to receive timely updates on event changes. In light of this, a communication system is provided, offering private and group chat options that enable participants to easily connect with organizers and fellow participants.

##### Organizer

Organizers emphasize the importance of quickly disseminating event updates and efficiently responding to participant queries. To accommodate this need, the system is designed to include features for batch notification sending, private and group chats for fielding queries, and the setup of quick replies.

#### Forum System

Both participants and organizers recognize the value of a platform that facilitates the sharing and evaluation of events through posts, comments, and multimedia content. As a solution, a forum system is introduced, where users can publish posts, comment on them, and share images and videos, fostering a vibrant community centered around event experiences and feedback.

#### User Space

GalaGuide's user space offers a personalized dashboard where users can effortlessly manage and review their interactions. This includes a history of events, favorite items, payment records, and upcoming reservations, simplifying the tracking of activities and financial transactions. Users also have the ability to customize their profiles and adjust settings to suit their preferences, from notification management to privacy controls. This streamlined and customizable approach ensures a user-friendly experience, making it easy for individuals to navigate their personal journey with GalaGuide, aligning with the platform's dedication to convenience and personalization.

#### Chat Room
##### Private Chat
Users can chat with each other privately by clicking on others' avatars and sending private messages. 
##### Group Chat
Groups are created by users and can be joined by other users. A group live message will be broadcasted to all the members of the group who is online by then. 
  
#### AI Assistant

Gal, GalaGuide's smart assistant, transforms the way users interact with databases by understanding natural language and translating it into SQL queries. It leverages advanced natural language processing (NLP) to decode user intents from casual conversations and then crafts these into SQL commands to fetch the desired data. This breakthrough technology not only simplifies database access for non-technical users but also continuously learns from interactions, enhancing its response accuracy over time. By seamlessly integrating into GalaGuide, Gal provides a user-friendly interface for diverse inquiries, ranging from local recommendations to complex data analysis, making information retrieval intuitive and efficient.


#### Events Exploring and Recommendation System

Participants search for personalized event recommendations that match their interests and seamlessly fit into their schedules. To cater to this, an automated recommendation system is implemented, offering tailored event suggestions based on individual preferences and availability, thereby enhancing the event discovery process significantly.


#### Currency System

Guiro is the virtual currency of GalaGuide. Users can obtain guiros by the following ways:

- Punch in every day.
- Share experiences about events you have participated.
- Contribute to the community of GalaGuide in other ways.

###  Non-functional requirements

#### Performance Requirements

- **Response Time**: The user interface should have a response time of no more than 2 seconds to ensure smooth user operations.
- **Concurrency**: The system should be capable of supporting at least 1000 concurrent users to accommodate peak usage demands.

#### Usability Requirements

- **User Interface**: Provide an intuitive and user-friendly interface that supports multiple languages, ensuring ease of use for users from diverse backgrounds.
- **Documentation**: Offer comprehensive user manuals and online help documentation to assist users in resolving operational queries.

#### Security Requirements

- **Data Encryption**: Sensitive information, such as personal data and payment details, should be encrypted during transmission and storage.
- **Access Control**: Implement a role-based access control system to ensure that only authorized users can access specific functionalities and data.

#### Maintainability Requirements

- **Code Quality**: Use clear coding standards and a robust software architecture to ensure code readability and maintainability.
- **Updates and Upgrades**: The system's design should support easy software updates and functionality upgrades to adapt to changing future needs.

#### Scalability Requirements

- **Modular Design**: Employ a modular design to facilitate the addition of new features or modification of existing ones, addressing the evolving market demands.
- **Resource Management**: The system should automatically adjust resource utilization based on the load to optimize performance and costs.



### Data requirements

#### Participants

##### User Information

- Name, student/employee ID, department/faculty.
- Contact details, such as email address and phone number.

##### Preference Settings

- Interested types of events (academic, sports, entertainment, etc.).
- Preferences for receiving notifications (e.g., updates on events, message alerts).

##### Activity Participation History

- List of past events attended and related feedback.
- Record of event check-ins.

##### Schedule Information

Personal schedules and free times for event recommendation purposes.

##### Academic/Teaching Related Information

- Information on courses selected/taught.
- Participation in or organization of other events.

#### Organizers

##### User Information

- Organization name, type (student group, academic organization, etc.).
- Contact information and responsible person details.

##### Event Information

- Details of events posted, including time, location, participant limit, etc.
- Updates on event status, such as changes, cancellations.

##### Participant Feedback Collection

- Feedback and evaluations from event participants.
- Attendance records of participants.

##### Historical Event Data

- Records and participation details of past organized events.
- Success stories and learning points from historical events.

##### Resource Needs and Management

- List of resources required for events (e.g., venues, equipment).
- Records of resource usage and allocation.

#### Note

We will obtaining user consent before collecting and using data, and clarifying the purpose of data use.



### Technical requirements

> Warning: Need to checkout.

---
#### Vue
#### Node.js
---
#### Exposed
#### Ktor
---

## AI Usage

For the tasks of conducting preliminary requirement analysis, generating user stories, and creating issues or tasks, we incorporated AI as a tool for further optimization after completing these tasks manually. Our approach was to leverage AI judiciously, enhancing our processes without becoming overly reliant on it.

### Preliminary Requirement Analysis

In the task of identifying functional and non-functional requirements, AI proved to be highly effective. After our team outlined the initial set of requirements, we utilized AI to integrate these disparate features and offer further suggestions. This AI involvement enabled us to consolidate human-generated ideas into a more cohesive and comprehensive set of requirements, demonstrating AI's strength in synthesizing and enhancing human input.

### Generating User Stories

When it came to directly generating user stories using AI, the results were not satisfactory due to the lack of a genuine campus experience foundation. However, AI significantly contributed when used to role-play based on user stories already proposed by humans. By simulating conversations between different campus personas, AI helped us better understand the emphasis of user needs, refining our approach. This method of engaging AI for role-playing provided valuable insights and adjustments to our initial ideas.

### Generating Issues or Tasks

Directly generating tasks and issues with AI did not yield positive outcomes, as this process heavily depends on the team's shared understanding and individual perspectives. Nevertheless, AI became highly beneficial when tasked with providing opinions on already identified tasks and issues from various roles, such as a project manager, a software developer, or an investor. This application of AI allowed us to view our project through different lenses, offering diverse perspectives that enriched our decision-making and prioritization processes.

In summary, while AI was instrumental in optimizing certain aspects of our project development, its utility was maximized when used to augment human-led activities rather than replace them. This balanced approach ensured that AI's capabilities were harnessed to refine and enhance our work, grounded in the real-world context and collective expertise of our team.
