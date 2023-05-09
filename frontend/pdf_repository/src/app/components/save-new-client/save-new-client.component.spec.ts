import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SaveNewClientComponent } from './save-new-client.component';

describe('SaveNewClientComponent', () => {
  let component: SaveNewClientComponent;
  let fixture: ComponentFixture<SaveNewClientComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SaveNewClientComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SaveNewClientComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
