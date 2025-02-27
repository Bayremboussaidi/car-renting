
import { Component, OnInit, HostListener, ElementRef, ViewChild } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  isScrolled = false;
  isMenuOpen = false;
  @ViewChild('menu', { static: false }) menu!: ElementRef;

  navLinks = [
    { path: '/home', display: 'Accueil' },
    { path: '/about', display: 'À propos' },
    { path: '/listcars', display: 'Voitures' },
    { path: '/blogs', display: 'Blogs' },
    { path: '/contact', display: 'Contact' }
  ];

  user: any; // You can fetch the user details from AuthService

  constructor() {}

  ngOnInit() {}

  @HostListener("window:scroll", [])
  onWindowScroll() {
    this.isScrolled = window.scrollY > 80;
  }

  toggleMenu() {
    this.isMenuOpen = !this.isMenuOpen;
  }
}
