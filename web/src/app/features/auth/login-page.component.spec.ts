import { TestBed, ComponentFixture } from '@angular/core/testing';
import { LoginPageComponent } from './login-page.component';

describe('LoginPageComponent', () => {
  let fixture: ComponentFixture<LoginPageComponent>;
  let component: LoginPageComponent;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LoginPageComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(LoginPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  describe('Component initialization', () => {
    it('should create the component', () => {
      expect(component).toBeTruthy();
    });

    it('should initialize form with empty fields', () => {
      expect(component.loginForm.value).toEqual({
        email: '',
        password: '',
      });
    });
  });

  describe('Form validation', () => {
    it('should be invalid when email is empty', () => {
      component.loginForm.patchValue({ email: '', password: '123456' });
      expect(component.loginForm.invalid).toBe(true);
    });

    it('should be invalid when email is invalid', () => {
      component.loginForm.patchValue({ email: 'invalid-email', password: '123456' });
      expect(component.loginForm.invalid).toBe(true);
    });

    it('should be invalid when password is empty', () => {
      component.loginForm.patchValue({ email: 'test@example.com', password: '' });
      expect(component.loginForm.invalid).toBe(true);
    });

    it('should be invalid when password is too short', () => {
      component.loginForm.patchValue({ email: 'test@example.com', password: '123' });
      expect(component.loginForm.invalid).toBe(true);
    });

    it('should be valid with correct values', () => {
      component.loginForm.patchValue({ email: 'test@example.com', password: '123456' });
      expect(component.loginForm.valid).toBe(true);
    });
  });

  describe('loginAsDoctor method', () => {
    it('should fill form with doctor credentials', () => {
      component.loginAsDoctor();
      expect(component.loginForm.value).toEqual({
        email: 'doctor@wardcare.fr',
        password: '123456',
      });
    });
  });

  describe('Template integration', () => {
    it('should disable submit when form is invalid', () => {
      component.loginForm.patchValue({ email: '', password: '' });
      fixture.detectChanges();
      const submitButton = fixture.nativeElement.querySelector(
        'button[type="submit"]',
      ) as HTMLButtonElement;
      expect(submitButton.disabled).toBe(true);
    });

    it('should enable submit when form is valid', () => {
      component.loginForm.patchValue({ email: 'test@example.com', password: '123456' });
      fixture.detectChanges();
      const submitButton = fixture.nativeElement.querySelector(
        'button[type="submit"]',
      ) as HTMLButtonElement;
      expect(submitButton.disabled).toBe(false);
    });

    it('should disable submit when loading', () => {
      component.loading.set(true);
      fixture.detectChanges();
      const submitButton = fixture.nativeElement.querySelector(
        'button[type="submit"]',
      ) as HTMLButtonElement;
      expect(submitButton.disabled).toBe(true);
    });

    it('should display server error when present', () => {
      component.serverError.set('Test error');
      fixture.detectChanges();
      const errorElement = fixture.nativeElement.querySelector('.server-error') as HTMLElement;
      expect(errorElement?.textContent).toContain('Test error');
    });

    it('should fill form when doctor login button is clicked', () => {
      const doctorButton = fixture.nativeElement.querySelector(
        'button[type="button"]',
      ) as HTMLButtonElement;
      doctorButton.click();
      expect(component.loginForm.value).toEqual({
        email: 'doctor@wardcare.fr',
        password: '123456',
      });
    });
  });
});
