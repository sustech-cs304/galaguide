[toc]



# GALAGuide — Software Engineering Proposal

> Name: Lianji Bai    SID: 12110201

## Project Overview

### What is GALA Guide?

As a complete campus event assistant platform, we aim to change the way events are managed by including everything needed for planning and running events. Our platform connects event organizers and attendees, making communication easier and organizing smoother. We also provide tailored event suggestions, helping students find activities that match their interests easily, even with their full study schedules. Our goal is to make campus life better by offering simple access to many events, creating an active and connected student community.

### Who are our key customers?

Our main users are students, teachers, and event planners in the college community, looking for a simple and efficient way to manage and join campus events. This platform is made for all kinds of events, from educational talks and workshops to social meets and sports, providing complete services for any campus activity.

### What are the functionalities of our GALAGuide?

In short, our software solution meets the specific needs and problems of both attendees and event planners in the event scene. By offering focused features for each group, we aim to make finding, booking, managing, and taking part in events easier, improving the experience for everyone involved.

##  Preliminary Requirement Analysis

### Functional requirements & Features

#### Event Center

##### Participant

Participants need a simple way to find events that match their interests and also want access to clear, current information about the event, such as where and when it is. So, our software gives them a tool to discover events that lets them search and choose what they're interested in, along with giving them detailed information about the event, like maps and when it's happening.

##### Organizer

Organizers need an all-in-one place to easily share their events and also want to be able to update event details quickly to make sure the information is correct and up-to-date for participants. Our solution provides organizers with a straightforward platform for sharing events and making changes to the details, making it easier to manage the event and engage with participants.

#### Event Booking

##### Participant

Participants want an easy and straightforward process to sign up for events. They also want to see the details they've submitted and get an idea of how many people are expected to join. To meet these needs, we provide a simple event booking system. This lets participants easily enter their details, check their sign-up status, and see how many people are expected to attend.

##### Organizer

Organizers need a simple way to keep track of who has signed up for their events and to check attendees in efficiently. Our platform offers tools that help organizers keep an eye on bookings and carry out group check-ins. This makes it easier to manage who's coming to the event.

#### Messaging System

##### Participant

Participants want an easy and fast way to talk to event organizers and other attendees, and to get updates on any changes to the event. Our messaging system offers instant notification of changes to booked activities, which allows participants to quickly understand the latest situation and make decisions.

##### Organizer

Organizers stress the need to send out event updates quickly and answer any questions from participants efficiently. Our system is equipped with tools for sending notifications to many people at once, conducting private and group chats to address questions, and creating quick replies. This setup helps organizers communicate effectively with participants.

#### Forum System

Both participants and organizers see the benefit of having a space where they can share and discuss events.  To meet this need, we offer a forum system.  Here, users can write posts, leave comments, and share photos and videos.  This creates an active community focused on sharing experiences and feedback about events.

#### User Space

GalaGuide provides a personal dashboard for users, making it easy to keep track of and manage their activities.  This includes a record of past events, favorite picks, payments, and future bookings.  It helps users monitor their event participation and money spent.  Users can also personalize their profiles and settings, like how they receive notifications or manage their privacy.  This tailored and user-friendly setup ensures a smooth experience, helping everyone make the most of their time with GalaGuide, in line with our commitment to ease and customization.

#### Chat Room

##### Private Chat

Users can have one-on-one conversations by clicking on someone's avatar and sending them a private message. This feature allows for personal and direct communication within the platform.

##### Group Chat

Users can create or join groups. When a message is sent in a group, it's instantly shared with all online members. This enables collective discussions and information sharing among users who share similar interests.

#### AI Assistant

Gal, the GalaGuide's intelligent assistant, revolutionizes user interaction with databases by interpreting natural language into SQL queries. It utilizes cutting-edge natural language processing (NLP) technology to understand what users mean in everyday language, converting these understandings into SQL commands to retrieve the needed information. This innovative approach makes it easier for those without technical skills to access databases and improves with every interaction, becoming more accurate in understanding and responding to user queries. Gal is integrated into GalaGuide, offering a simple way for users to ask various questions, from seeking local event recommendations to performing detailed data analyses, making getting information straightforward and effective.

#### Events Exploring and Recommendation System

Participants can find events tailored to their interests and schedules through an automated recommendation system. This system suggests events based on users' preferences and available times, greatly improving the process of discovering new events.

#### Currency System

Guiro is the virtual currency within GalaGuide. Users can earn guiros through several activities:

- Checking in daily.
- Sharing their experiences about events they've attended.
- Contributing to the GalaGuide community in various ways.



###  Non-functional requirements

#### Performance Requirements

- **Response Time**: The user interface should load within 2000 milliseconds for a smooth experience.
- **Concurrency**: The system should handle 1000 users at the same time, especially during busy periods.

#### Usability Requirements

- **Localization**: The interface should be easy to use and support different languages, making it accessible to a wide range of users.
- **Documentation**: Provide detailed guides and online help to answer users' questions about how to use the system.

#### Security Requirements

- **Data Encryption**: Keep sensitive data like personal and payment information safe by encrypting it when sending and storing.
- **Access Control**: Set up a system where users have different access levels based on their roles to make sure only the right people can see certain information and features.

#### Maintainability Requirements

- **Code Standards**: Follow clear rules for writing code and designing the software so it's easy to read and keep up to date.
- **Flexible and Modular System Updates**: The system is made to easily accept new updates and improve functions, staying flexible for future needs. It uses a modular approach, making it easy to add or change features as market needs evolve.



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



## AI Usage

For the tasks of conducting preliminary requirement analysis, generating user stories, and creating issues or tasks, we incorporated AI as a tool for further optimization after completing these tasks manually. Our approach was to leverage AI judiciously, enhancing our processes without becoming overly reliant on it.

### Preliminary Requirement Analysis

In the task of identifying functional and non-functional requirements, AI proved to be highly effective. After our team outlined the initial set of requirements, we utilized AI to integrate these disparate features and offer further suggestions. This AI involvement enabled us to consolidate human-generated ideas into a more cohesive and comprehensive set of requirements, demonstrating AI's strength in synthesizing and enhancing human input.

### Generating User Stories

When it came to directly generating user stories using AI, the results were not satisfactory due to the lack of a genuine campus experience foundation. However, AI significantly contributed when used to role-play based on user stories already proposed by humans. By simulating conversations between different campus personas, AI helped us better understand the emphasis of user needs, refining our approach. This method of engaging AI for role-playing provided valuable insights and adjustments to our initial ideas.

### Generating Issues or Tasks

Directly generating tasks and issues with AI did not yield positive outcomes, as this process heavily depends on the team's shared understanding and individual perspectives. Nevertheless, AI became highly beneficial when tasked with providing opinions on already identified tasks and issues from various roles, such as a project manager, a software developer, or an investor. This application of AI allowed us to view our project through different lenses, offering diverse perspectives that enriched our decision-making and prioritization processes.

In summary, while AI was instrumental in optimizing certain aspects of our project development, its utility was maximized when used to augment human-led activities rather than replace them. This balanced approach ensured that AI's capabilities were harnessed to refine and enhance our work, grounded in the real-world context and collective expertise of our team.



### Technical requirements


---

#### Frontend Framework

 Vue.js + Node.js

#### Backend Framework: Kotlin

Exposed + Ktor
