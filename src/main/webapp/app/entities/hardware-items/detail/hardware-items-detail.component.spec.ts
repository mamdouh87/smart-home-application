import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HardwareItemsDetailComponent } from './hardware-items-detail.component';

describe('HardwareItems Management Detail Component', () => {
  let comp: HardwareItemsDetailComponent;
  let fixture: ComponentFixture<HardwareItemsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HardwareItemsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ hardwareItems: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HardwareItemsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HardwareItemsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load hardwareItems on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.hardwareItems).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
