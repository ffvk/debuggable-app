import { Component } from '@angular/core';
import { registerPlugin, Capacitor } from '@capacitor/core';
import { AlertController } from '@ionic/angular';

interface OnboardingPluginType {
  triggerOnboardingSDK(): Promise<void>;
}

// Fallback implementation for web
const OnboardingPluginWeb: OnboardingPluginType = {
  async triggerOnboardingSDK() {
    // Simulate the onboarding process for web
    return new Promise((resolve) => {
      setTimeout(() => {
        console.log('Onboarding completed on web.');
        resolve();
      }, 1000);
    });
  },
};

const OnboardingPlugin =
  Capacitor.getPlatform() === 'web'
    ? OnboardingPluginWeb
    : registerPlugin<OnboardingPluginType>('OnboardingPlugin');

@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {
  constructor(private alertController: AlertController) {}

  async triggerOnboardingSDK() {
    try {
      await OnboardingPlugin.triggerOnboardingSDK();
      this.showAlert('Success', 'Onboarding SDK triggered successfully!!');
    } catch (error: any) {
      console.error('Error triggering onboarding SDK:', error);
      this.showAlert('Error', ` ${error.message || error}`);
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
