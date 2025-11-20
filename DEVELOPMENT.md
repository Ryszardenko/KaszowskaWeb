# Magdalena Kaszowska - Permanent Makeup Website

A modern, minimalist website for permanent makeup professional Magdalena Kaszowska, built with Kotlin and Compose for Web.

## Features

- **Hero Section**: Full-screen landing with elegant introduction
- **About Section**: Professional introduction and credentials
- **Services Section**: Display of offered services with pricing and image gallery carousel
- **Contact Section**: Contact form and business information
- **Instagram Integration**: Social media links
- **Fixed Navigation**: Smooth navigation bar that stays on top
- **Modern Design**: Elegant color scheme inspired by professional beauty industry websites

## Tech Stack

- **Kotlin** 2.2.20
- **Compose Multiplatform** 1.9.1
- **Compose for Web** (JS target)

## Project Structure

```
composeApp/src/webMain/
├── kotlin/com/machmudow/kaszowska/
│   ├── App.kt                      # Main app component
│   ├── components/
│   │   ├── HeroSection.kt          # Landing section
│   │   ├── AboutSection.kt         # About/Introduction
│   │   ├── ServicesSection.kt      # Services with carousel
│   │   ├── ContactSection.kt       # Contact form
│   │   ├── TopNavigation.kt        # Fixed navigation
│   │   └── Footer.kt               # Footer
│   └── theme/
│       └── Theme.kt                # Color scheme and styling
└── resources/
    ├── index.html
    └── styles.css
```

## Color Scheme

The website uses a sophisticated color palette:
- **Cream**: #F5F1ED - Background
- **Dark Brown**: #2C2421 - Text and footer
- **Gold**: #C9A87C - Accents and CTAs
- **Soft Beige**: #E8DFD8 - Section backgrounds

## Running the Project

### Development Server
```bash
./gradlew jsBrowserDevelopmentRun
```

The website will be available at `http://localhost:8080`

### Production Build
```bash
./gradlew jsBrowserProductionWebpack
```

Build output will be in `composeApp/build/dist/js/productionExecutable/`

## Customization

### Adding Content

1. **Hero Section**: Edit `HeroSection.kt` to update the main heading and tagline
2. **About Text**: Modify `AboutSection.kt` to add your professional story
3. **Services**: Update the services list in `ServicesSection.kt`
4. **Contact Info**: Change contact details in `ContactSection.kt`

### Adding Images

Images can be added to `composeApp/src/webMain/composeResources/drawable/` and referenced using Compose Resources.

### Color Changes

Modify colors in `theme/Theme.kt` in the `KaszowskaColors` object.

## TODO / Future Enhancements

- [ ] Add actual images for hero, about, and gallery
- [ ] Implement smooth scroll to section on navigation click
- [ ] Add parallax scrolling effects
- [ ] Implement carousel auto-play and swipe gestures
- [ ] Add form validation and submission (integrate with email service)
- [ ] Add fade-in animations on scroll
- [ ] Responsive design for mobile devices
- [ ] Add loading states and transitions
- [ ] Integrate Instagram API for live feed
- [ ] Add booking system integration
- [ ] Multi-language support (PL/EN)

## License

© 2025 Magdalena Kaszowska. All rights reserved.

