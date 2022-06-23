import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { RequirementItemDetailComponent } from './requirement-item-detail.component';

describe('RequirementItem Management Detail Component', () => {
  let comp: RequirementItemDetailComponent;
  let fixture: ComponentFixture<RequirementItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [RequirementItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ requirementItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(RequirementItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(RequirementItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load requirementItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.requirementItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
