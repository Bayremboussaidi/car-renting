import { Component, OnInit, HostListener } from '@angular/core';

@Component({
  selector: 'app-become-driver',
  templateUrl: './become-driver.component.html',
  styleUrls: ['./become-driver.component.css']
})
export class BecomeDriverComponent implements OnInit {
  backgroundImage = '../../../assets/all-images/drive.jpg';

  testimonials = [
    {
      text: "Vous voulez gagner de l'argent avec nous ? Alors ne soyez pas en retard.",
      author: "",
      company: "myloc"
    },
    {
      text: "Les opportunités comme celle-ci ne se présentent pas deux fois. Réservez sans attendre !.",
      author: "",
      company: "myloc"
    },
    {
      text: "Confort, puissance, élégance… Trouvez la voiture qui vous correspond !",
      author: "",
      company: "myloc"
    }
  ];

  activeSlide = 0;
  interval: any;
  cursorStyle = 'default';

  ngOnInit() {
    this.startAutoSlide();
  }

  setActiveSlide(index: number) {
    this.activeSlide = index;
    this.resetAutoSlide();
  }

  nextSlide() {
    this.activeSlide = (this.activeSlide + 1) % this.testimonials.length;
    this.resetAutoSlide();
  }

  prevSlide() {
    this.activeSlide = (this.activeSlide - 1 + this.testimonials.length) % this.testimonials.length;
    this.resetAutoSlide();
  }

  startAutoSlide() {
    this.interval = setInterval(() => {
      this.nextSlide();
    }, 5000); // Change every 5 seconds
  }

  resetAutoSlide() {
    clearInterval(this.interval);
    this.startAutoSlide();
  }

  /* Detect Mouse Movement to Change Cursor */
  @HostListener('document:mousemove', ['$event'])
  handleMouseMove(event: MouseEvent) {
    const screenWidth = window.innerWidth;
    const mouseX = event.clientX;

    if (mouseX < screenWidth / 2) {
      this.cursorStyle = "w-resize"; // Change to `<` when moving left
    } else {
      this.cursorStyle = "e-resize"; // Change to `>` when moving right
    }
  }

  /* Handle Click to Change Slide */
  @HostListener('document:click', ['$event'])
  handleClick(event: MouseEvent) {
    const screenWidth = window.innerWidth;
    const clickX = event.clientX;

    if (clickX < screenWidth / 2) {
      this.prevSlide(); // Click left = Previous slide
    } else {
      this.nextSlide(); // Click right = Next slide
    }
  }
}
