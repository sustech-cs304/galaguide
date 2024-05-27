# GalaGuide

![logo](./galaguide/src/assets/logo.png)

*GalaGuide* is an MIT-licensed open-source cross-platform software application as your personal manager for campus events & reservations based on Electron, Vue, and Ktor.

GitHub: [![GitHub](https://img.shields.io/badge/GitHub-GalaGuide-blue?logo=github)](https://github.com/sustech-cs304/team-project-24spring-23333)

## Features

- [x] Event management
    - [x] Smart recommendation
    - [x] Smooth event creation experience
    - [x] Easy-to-use event search
    - [x] Simple event registration
    - [x] Just-in-time event notification
    - [x] Step-by-step guide for event participation

- [x] Reservation management
    - [x] Scalable reservation system for personal and group use
    - [x] Able to handle various types of reservations
    - [x] Easy information gathering for reservation
    - [x] Clear reservation status display
    - [x] Reminder for reservation status

- [x] Live chat: Communicate with others in real-time
    - [x] Real-time chat with other users
    - [x] Group chat in the same event
    - [x] Chat history for each event updated in real-time
    - [ ] Chatbot for common questions

- [x] Forum: Share your thoughts and experiences
    - [x] Post and reply to threads
    - [x] Upvote and downvote threads
    - [x] Search for threads
    - [x] Filter threads by category

- [x] AI assistant
    - [x] Smart recommendation for events and reservations
    - [x] Chattable AI assistant
    - [ ] Voice assistant for hands-free operation

- [x] Interactive map
    - [x] Campus map with event locations
    - [ ] Navigation to event locations
    - [ ] Real-time location tracking


## Getting Started

Take a look at the [wiki](https://github.com/sustech-cs304/team-project-24spring-10/wiki) for more information about the project.

The GitHub repository provides both backend and frontend code. The backend is based on Ktor, and the frontend is based on Vue. You could also find the pre-built executable files in the release page. The following instructions will help you to build the project from source code. So if you only want to run the application using the executable file, you can skip the following steps.

## Using pre-built executable files

TODO

## Building from source code

### Prerequisites

- Node.js

TODO: Add more prerequisites

### Installation

1. Clone the repository

```bash
git clone https://github.com/sustech-cs304/team-project-24spring-10.git
```

2. Enter the directory `galaguide/` and install the dependencies for the frontend

```bash
npm install
```

3. Build the backend

TODO

4. Install the requirements for the backend for AI

```bash
cd ../ai
pip install -r requirements.txt
```

### Usage

1. Run the backend

```bash
TODO
cd ../ai
python app.py
```

2. Enter the directory `galaguide/` and run the frontend

```bash
npm run electron:serve
```

After running the frontend, you should see the application window.

## Other

### Future work

The objective of GalaGuide is to provide a comprehensive event and reservation management system for campus users. The current version of GalaGuide is a prototype, and there are many features that can be added in the future. You could find the future work in the [wiki](https://github.com/sustech-cs304/team-project-24spring-10/wiki) and if you have any ideas, feel free to open an issue or pull request.

### Contributing

Any contributing and pull requests are welcomed. However, before you plan to implement some features or try to fix an uncertain issue, it is recommended to read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

### About the logo

The logo of GalaGuide is designed by [Jianzheng Huang](https://github.com/Cara-Zinc) using AI tools. The logo is inspired by the concept of a compass, which represents the guidance and direction provided by GalaGuide. The logo is designed to be simple and easy to recognize, with a color scheme that is easy on the eyes.

### Contact

If you have any questions or suggestions, feel free to contact us via email or GitHub issues. You could find the contact information in the [wiki](https://github.com/sustech-cs304/team-project-24spring-10/wiki)

### Sponsoring

Open source development is not free and cannot be sustained without your support. If you like GalaGuide and want to support the development, you could sponsor us via GitHub Sponsors. You could find the sponsor button on the top right of the repository page. ❤️

### Acknowledgements

GalaGuide is originally a team project for the course CS304 Software Engineering (Spring 2024) at Southern University of Science and Technology (SUSTech). The team members are (in ascending order of Pinyin):

- [Hanran Zhou](https://github.com/NoMathExpectation)
- [Haodong He](https://github.com/qwurd231)
- [Jianzheng Huang](https://github.com/Cara-Zinc)
- [Lianji Bai](https://github.com/WinwayCome)
- [Qiwen Xiao](https://github.com/Charley-xiao)

We would like to thank the course instructor [Yida Tao](https://yidatao.github.io/) and teaching assistants for their guidance and support during the development of GalaGuide, the open-source community for providing us with the tools and resources to build GalaGuide, and also ourselves for the hard work and dedication to the project throughout the semester.

And lastly, a deep bow to you for using GalaGuide and supporting open-source development.

### License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
