import { Component } from '@angular/core';
import { registerPlugin, Capacitor } from '@capacitor/core';
import { AlertController } from '@ionic/angular';

interface OnboardingPluginType {
  triggerOnboardingSDK(): Promise<void>;
}

const OnboardingPlugin =
  registerPlugin<OnboardingPluginType>('OnboardingPlugin');

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  constructor(private alertController: AlertController) {}

  async triggerOnboardingSDK() {
    if (Capacitor.getPlatform() !== 'web') {
      try {
        await OnboardingPlugin.triggerOnboardingSDK();
        this.showAlert('Success', 'Onboarding SDK triggered successfully!');
      } catch (error) {
        console.error('Error triggering onboarding SDK:', error);
        this.showAlert('Error', 'Failed to trigger onboarding SDK.');
      }
    } else {
      console.warn('Onboarding SDK is not supported on the web platform.');
      this.showAlert(
        'Warning',
        'Onboarding SDK is not supported on the web platform.',
      );
    }
  }

  async showAlert(header: string, message: string) {
    const alert = await this.alertController.create({
      header: header,
      message: message,
      buttons: ['OK'],
    });
    await alert.present();
  }
}
