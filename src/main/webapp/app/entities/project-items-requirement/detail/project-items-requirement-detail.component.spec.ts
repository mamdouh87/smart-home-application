import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ProjectItemsRequirementDetailComponent } from './project-items-requirement-detail.component';

describe('ProjectItemsRequirement Management Detail Component', () => {
  let comp: ProjectItemsRequirementDetailComponent;
  let fixture: ComponentFixture<ProjectItemsRequirementDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProjectItemsRequirementDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ projectItemsRequirement: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(ProjectItemsRequirementDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(ProjectItemsRequirementDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load projectItemsRequirement on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.projectItemsRequirement).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
