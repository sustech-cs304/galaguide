# Contributing to GalaGuide

First off, thank you for considering contributing to GalaGuide! It's people like you that make GalaGuide such a great software.

GalaGuide is an open source project and we love to receive contributions from our community — you! There are many ways to contribute, from writing tutorials or examples, improving the documentation, submitting bug reports and feature requests or writing code which can be incorporated into GalaGuide itself.

Following these guidelines helps to communicate that you respect the time of the developers managing and developing this open source project. In return, they should reciprocate that respect in addressing your issue, assessing changes, and helping you finalize your pull requests.

## Table of Contents

[How Can I Contribute?](#how-can-i-contribute)

- [Reporting Bugs](#reporting-bugs)

- [Suggesting Enhancements](#suggesting-enhancements)

- [Your First Code Contribution](#your-first-code-contribution)

- [Pull Request Checklist](#pull-request-checklist)

[Styleguides](#styleguides)

## How Can I Contribute?

### Reporting Bugs

This section guides you through submitting a bug report for GalaGuide. Following these guidelines helps maintainers and the community understand your report, reproduce the behavior, and find related reports.

#### Before Submitting

Before creating bug reports, please follow the instructions below first as you might find out that it is unnecessary to open a bug report.

1. **Check the discussions** on the GalaGuide repository for common questions and issues.

2. **Perform a cursory search** to see if the problem has already been reported. If it has and the issue is still open, add a comment to the existing issue instead of opening a new one.

3. **Check if the issue has been fixed**. It may also be that the bug has been fixed in the latest code. If possible, use the latest GalaGuide release to see if the problem still exists.

#### When Submitting

Bugs are tracked as [GitHub issues](https://guides.github.com/features/issues/). After you've determined which repository your bug is related to, create an issue on that repository and provide the following information.

When creating a bug report, please include as many details as possible. Fill out [the required template](.github/ISSUE_TEMPLATE/bug_report.md), the information it asks for helps us resolve issues faster:

- **Use a clear and descriptive title** for the issue to identify the problem.

- **Describe the exact steps which reproduce the problem** in as many details as possible.

- **Provide specific examples to demonstrate the steps**. Include links to files or GitHub projects, or copy/pasteable snippets, which you use in those examples. If you're providing snippets in the issue, use [Markdown code blocks](https://help.github.com/articles/markdown-basics/#multiple-lines).

- **Describe the behavior you observed after following the steps** and point out what exactly is the problem with that behavior.

- **Explain which behavior you expected to see instead and why**.

- **Include screenshots and animated GIFs** which show you following the described steps and clearly demonstrate the problem. If you use the keyboard while following the steps, **record the GIF with [LiceCap](https://www.cockos.com/licecap/)**. You can use this tool to record GIFs on Windows and MacOS.

- **If the problem is related to performance or memory**, include a CPU profile capture with your report.

- **If the problem wasn't triggered by a specific action**, describe what you were doing before the problem happened and share more information using the guidelines below.

Provide more context by answering these questions:

- **Did the problem start happening recently**? If so, what changes did you make to your environment or code?

- **What is your environment?** Include as many relevant details about the environment you experienced the bug in.

- **Can you reliably reproduce the issue?** If not, provide details about how often the problem happens and under which conditions it normally happens.

Include details about your configuration and environment:

- **Which version of GalaGuide are you using**? If you are building from source, **which version of Node.js, npm, ktor, Electron, etc. are you using**?

- **What's the name and version of the OS you're using**?

- **Which packages or dependencies are you using**? If you have a large list of dependencies, include the package.json, build.gradle, or pom.xml file.

### Suggesting Enhancements

This section guides you through submitting an enhancement suggestion for GalaGuide, including completely new features and minor improvements to existing functionality. Following these guidelines helps maintainers and the community understand your suggestion and find related suggestions.

#### Before Submitting

Before creating enhancement suggestions, please check the following list of enhancements suggestions, as you might find out that you don't need to create one. When you are creating an enhancement suggestion, please include as many details as possible. Fill in [the template](.github/ISSUE_TEMPLATE/feature_request.md), including the steps that you imagine you would take if the feature you're requesting existed.

- **Check if there's already a release of GalaGuide that has the enhancement**. If there is, upgrade to that version and check if the enhancement is working.

- **Check if the enhancement has already been suggested**. If it has, add a comment to the existing issue instead of opening a new one.

#### When Submitting

Enhancement suggestions are tracked as [GitHub issues](https://guides.github.com/features/issues/). After you've determined which repository your enhancement is related to, create an issue on that repository and provide the following information:

- **Use a clear and descriptive title** for the issue to identify the suggestion.

- **Provide a step-by-step description of the suggested enhancement** in as many details as possible.

- **Provide specific examples to demonstrate the steps**. Include copy/pasteable snippets which you use in those examples, as Markdown code blocks.

- **Describe the current behavior** and **explain which behavior you expected to see instead** and why.

- **Explain why this enhancement would be useful** to most GalaGuide users.

- **Specify which version of GalaGuide you're using**.

- **Specify the name and version of the OS you're using**.

### Your First Code Contribution

Unsure where to begin contributing to GalaGuide? You can start by looking through these `good-first-issue` and `help-wanted` issues:

- [Good first issues](https://github.com/search?utf8=%E2%9C%93&q=is%3Aopen+is%3Aissue+label%3Abeginner+label%3Ahelp-wanted+user%3Agalaguide+sort%3Acomments-desc) - issues which should only require a few lines of code, and a test or two.

- [Help wanted issues](https://github.com/search?utf8=%E2%9C%93&q=is%3Aopen+is%3Aissue+label%3Abeginner+label%3Ahelp-wanted+user%3Agalaguide+sort%3Acomments-desc) - issues which should be a bit more involved than `good-first-issue` issues.

Both issue lists are sorted by the total number of comments. While not perfect, the number of comments is a reasonable proxy for the impact a given change will have. These issues are perfect for new contributors, and an easy way to get started with GalaGuide.

### Pull Request Checklist

Before you submit a pull request, please make sure you have followed these guidelines:

1. Follow the [styleguides](#styleguides).

2. Run `npm run test` to assure nothing is broken.

3. If you've added code that should be tested, add tests.

While the checklist above is not exhaustive, it should help you get started. If your pull request is not passing the tests, you will get a message. If you don't, please add tests and make sure they pass.

## Styleguides

### Git Commit Messages

- Use the present tense ("Add feature" not "Added feature").

- Use the imperative mood ("Move cursor to..." not "Moves cursor to...").

- Limit the first line to 72 characters or less.

- Reference issues and pull requests liberally after the first line.

- When only changing documentation, include `[ci skip]` in the commit title.

* Consider starting the commit message with an applicable emoji:
    * :art: `:art:` when improving the format/structure of the code
    * :racehorse: `:racehorse:` when improving performance
    * :non-potable_water: `:non-potable_water:` when plugging memory leaks
    * :memo: `:memo:` when writing docs
    * :penguin: `:penguin:` when fixing something on Linux
    * :apple: `:apple:` when fixing something on macOS
    * :checkered_flag: `:checkered_flag:` when fixing something on Windows
    * :bug: `:bug:` when fixing a bug
    * :fire: `:fire:` when removing code or files
    * :green_heart: `:green_heart:` when fixing the CI build
    * :white_check_mark: `:white_check_mark:` when adding tests
    * :lock: `:lock:` when dealing with security
    * :arrow_up: `:arrow_up:` when upgrading dependencies
    * :arrow_down: `:arrow_down:` when downgrading dependencies
    * :shirt: `:shirt:` when removing linter warnings

### JavaScript Styleguide

All JavaScript must adhere to [JavaScript Standard Style](https://standardjs.com/).

### Kotlin Styleguide

All Kotlin must adhere to [Kotlin Style Guide](https://kotlinlang.org/docs/reference/coding-conventions.html).