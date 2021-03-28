import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FiledragdropComponent } from './filedragdrop.component';

describe('FiledragdropComponent', () => {
  let component: FiledragdropComponent;
  let fixture: ComponentFixture<FiledragdropComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FiledragdropComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FiledragdropComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

});
